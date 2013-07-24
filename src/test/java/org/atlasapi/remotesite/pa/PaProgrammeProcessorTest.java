package org.atlasapi.remotesite.pa;

import static com.metabroadcast.common.time.DateTimeZones.UTC;
import static org.atlasapi.media.entity.MediaType.VIDEO;
import static org.atlasapi.media.entity.Publisher.METABROADCAST;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Iterator;
import java.util.List;

import org.atlasapi.media.channel.Channel;
import org.atlasapi.media.entity.Brand;
import org.atlasapi.media.entity.Container;
import org.atlasapi.media.entity.Described;
import org.atlasapi.media.entity.Film;
import org.atlasapi.media.entity.Image;
import org.atlasapi.media.entity.Item;
import org.atlasapi.media.entity.LookupRef;
import org.atlasapi.media.entity.Publisher;
import org.atlasapi.media.entity.Series;
import org.atlasapi.media.entity.Version;
import org.atlasapi.persistence.content.ContentResolver;
import org.atlasapi.persistence.content.ContentWriter;
import org.atlasapi.persistence.content.ResolvedContent;
import org.atlasapi.persistence.content.people.ItemsPeopleWriter;
import org.atlasapi.persistence.logging.AdapterLog;
import org.atlasapi.persistence.logging.NullAdapterLog;
import org.atlasapi.remotesite.pa.listings.bindings.Attr;
import org.atlasapi.remotesite.pa.listings.bindings.PictureUsage;
import org.atlasapi.remotesite.pa.listings.bindings.Pictures;
import org.atlasapi.remotesite.pa.listings.bindings.ProgData;
import org.atlasapi.remotesite.pa.listings.bindings.Season;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.ImmutableList;
import com.metabroadcast.common.base.Maybe;
import com.metabroadcast.common.time.Timestamp;

@RunWith(MockitoJUnitRunner.class)
public class PaProgrammeProcessorTest {

    private final int SUMMARY_BRAND_INDEX = 0;
    private final int ITEM_BRAND_INDEX = 1;
    private final int SUMMARY_SERIES_INDEX = 2;
    private final int ITEM_SERIES_INDEX = 3;

    private final ContentWriter contentWriter = mock(ContentWriter.class);
    private final ContentResolver contentResolver = mock(ContentResolver.class);
    private final ItemsPeopleWriter itemsPeopleWriter = mock(ItemsPeopleWriter.class);
    private final Described described = mock(Described.class);
    private final AdapterLog log = new NullAdapterLog();

    private Film film = new Film("http://pressassociation.com/films/5", "pa:f-5", Publisher.PA);
    @SuppressWarnings("deprecation")
    private Channel channel = new Channel(METABROADCAST, "c", "c", false, VIDEO, "c");

    @Captor
    private ArgumentCaptor<Iterable<Image>> imageListCaptor;
    
    private PaProgrammeProcessor progProcessor;
    
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        
        progProcessor = new PaProgrammeProcessor(contentWriter, contentResolver, itemsPeopleWriter, log);
    }
    
    @Test 
    public void testSetsPrimaryImages() {
        Pictures pictures = new Pictures();
        
        initialisePictures(pictures);
        
        progProcessor.selectImages(pictures, described, PaProgrammeProcessor.PA_PICTURE_TYPE_SERIES, PaProgrammeProcessor.PA_PICTURE_TYPE_BRAND, Maybe.<String>nothing());
        verify(described).setImages(imageListCaptor.capture());
        verify(described).setImage(PaProgrammeProcessor.PA_BASE_IMAGE_URL+"series1");
        verifyNoMoreInteractions(described);
        
        Iterator<Image> iter = imageListCaptor.getValue().iterator();
        assertEquals(PaProgrammeProcessor.NEW_IMAGE_BASE_IMAGE_URL+"series1", iter.next().getCanonicalUri());
        assertEquals(PaProgrammeProcessor.NEW_IMAGE_BASE_IMAGE_URL+"series2", iter.next().getCanonicalUri());
        assertEquals(PaProgrammeProcessor.NEW_IMAGE_BASE_IMAGE_URL+"series3", iter.next().getCanonicalUri());
        assertEquals(PaProgrammeProcessor.NEW_IMAGE_BASE_IMAGE_URL+"series4", iter.next().getCanonicalUri());
        assertEquals(PaProgrammeProcessor.NEW_IMAGE_BASE_IMAGE_URL+"series5", iter.next().getCanonicalUri());
        assertFalse(iter.hasNext());
    }
    
    @Test 
    public void testSetsPrimaryImagesSkippingFallback() {
        Pictures pictures = new Pictures();
        
        initialisePictures(pictures);
        
        progProcessor.selectImages(pictures, described, PaProgrammeProcessor.PA_PICTURE_TYPE_BRAND, PaProgrammeProcessor.PA_PICTURE_TYPE_SERIES, Maybe.<String>nothing());
        verify(described).setImages(imageListCaptor.capture());
        
        ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);
        verify(described, atLeastOnce()).setImage(stringCaptor.capture());
        verifyNoMoreInteractions(described);
        
        assertEquals(PaProgrammeProcessor.PA_BASE_IMAGE_URL+"brand1", stringCaptor.getValue()); // The latest version should be...
        
        Iterator<Image> iter = imageListCaptor.getValue().iterator();
        assertEquals(PaProgrammeProcessor.NEW_IMAGE_BASE_IMAGE_URL+"brand1", iter.next().getCanonicalUri());
        assertEquals(PaProgrammeProcessor.NEW_IMAGE_BASE_IMAGE_URL+"brand2", iter.next().getCanonicalUri());
        assertEquals(PaProgrammeProcessor.NEW_IMAGE_BASE_IMAGE_URL+"brand3", iter.next().getCanonicalUri());
        assertEquals(PaProgrammeProcessor.NEW_IMAGE_BASE_IMAGE_URL+"brand4", iter.next().getCanonicalUri());
        assertFalse(iter.hasNext());
    }
    
    @Test
    public void testUsesFallbackImage() {
        Pictures pictures = new Pictures();
        
        initialisePictures(pictures);
        
        progProcessor.selectImages(pictures, described, PaProgrammeProcessor.PA_PICTURE_TYPE_EPISODE, PaProgrammeProcessor.PA_PICTURE_TYPE_SERIES, Maybe.<String>nothing());
        verify(described).setImages(imageListCaptor.capture());
        verify(described).setImage(PaProgrammeProcessor.PA_BASE_IMAGE_URL+"series1");
        verifyNoMoreInteractions(described);
        
        Iterator<Image> iter = imageListCaptor.getValue().iterator();
        assertEquals(PaProgrammeProcessor.NEW_IMAGE_BASE_IMAGE_URL+"series1", iter.next().getCanonicalUri());
        assertFalse(iter.hasNext());
    }

    @Test
    public void testUsesPreferredFallbackImage() {
        Pictures pictures = new Pictures();
        
        initialisePictures(pictures);
        
        progProcessor.selectImages(pictures, described, PaProgrammeProcessor.PA_PICTURE_TYPE_EPISODE, PaProgrammeProcessor.PA_PICTURE_TYPE_SERIES, Maybe.just(PaProgrammeProcessor.PA_PICTURE_TYPE_BRAND));
        verify(described).setImages(imageListCaptor.capture());
        verify(described).setImage(PaProgrammeProcessor.PA_BASE_IMAGE_URL+"series1");
        verifyNoMoreInteractions(described);
        
        Iterator<Image> iter = imageListCaptor.getValue().iterator();
        assertEquals(PaProgrammeProcessor.NEW_IMAGE_BASE_IMAGE_URL+"series1", iter.next().getCanonicalUri());
        assertFalse(iter.hasNext());
    }

    @Test
    public void testUsesPreferredFallbackImageInOrder() {
        Pictures pictures = new Pictures();
        
        initialisePictures(pictures);
        
        progProcessor.selectImages(pictures, described, PaProgrammeProcessor.PA_PICTURE_TYPE_EPISODE, PaProgrammeProcessor.PA_PICTURE_TYPE_BRAND, Maybe.just(PaProgrammeProcessor.PA_PICTURE_TYPE_SERIES));
        verify(described).setImages(imageListCaptor.capture());
        
        ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);
        verify(described, atLeastOnce()).setImage(stringCaptor.capture());
        verifyNoMoreInteractions(described);
        
        assertEquals(PaProgrammeProcessor.PA_BASE_IMAGE_URL+"brand1", stringCaptor.getValue()); // The latest version should be...
        
        Iterator<Image> iter = imageListCaptor.getValue().iterator();
        assertEquals(PaProgrammeProcessor.NEW_IMAGE_BASE_IMAGE_URL+"brand1", iter.next().getCanonicalUri());
        assertFalse(iter.hasNext());
    }

    private void initialisePictures(Pictures pictures) {
        pictures.getPictureUsage().add(createPictureUsage(PaProgrammeProcessor.PA_PICTURE_TYPE_SERIES, "series1"));
        pictures.getPictureUsage().add(createPictureUsage(PaProgrammeProcessor.PA_PICTURE_TYPE_BRAND,  "brand1" ));
        pictures.getPictureUsage().add(createPictureUsage(PaProgrammeProcessor.PA_PICTURE_TYPE_SERIES, "series2"));
        pictures.getPictureUsage().add(createPictureUsage(PaProgrammeProcessor.PA_PICTURE_TYPE_SERIES, "series3"));
        pictures.getPictureUsage().add(createPictureUsage(PaProgrammeProcessor.PA_PICTURE_TYPE_BRAND,  "brand2" ));
        pictures.getPictureUsage().add(createPictureUsage(PaProgrammeProcessor.PA_PICTURE_TYPE_BRAND,  "brand3" ));
        pictures.getPictureUsage().add(createPictureUsage(PaProgrammeProcessor.PA_PICTURE_TYPE_SERIES, "series4"));
        pictures.getPictureUsage().add(createPictureUsage(PaProgrammeProcessor.PA_PICTURE_TYPE_SERIES, "series5"));
        pictures.getPictureUsage().add(createPictureUsage(PaProgrammeProcessor.PA_PICTURE_TYPE_BRAND,  "brand4" ));
    }
    
    private PictureUsage createPictureUsage(String type, String uri) {
        PictureUsage usage = new PictureUsage();
        usage.setType(type);
        usage.setvalue(uri);
        return usage;
    }
    

    @Test
    public void testPaSummaries() {
        Brand expectedItemBrand = new Brand("http://pressassociation.com/brands/5", "pa:b-5", Publisher.PA);
        Series expectedItemSeries= new Series("http://pressassociation.com/series/5-6", "pa:s-5-6", Publisher.PA);
        Brand expectedSummaryBrand = new Brand("http://summaries.pressassociation.com/brands/5", "pa:b-5", Publisher.PA_SERIES_SUMMARIES);
        Series expectedSummarySeries= new Series("http://summaries.pressassociation.com/series/5-6", "pa:s-5-6", Publisher.PA_SERIES_SUMMARIES);
        LookupRef expectedItemBrandLookupRef = LookupRef.from(expectedItemBrand);
        LookupRef expectedItemSeriesLookupRef = LookupRef.from(expectedItemSeries);

        ProgData inputProgData = setupProgData();
        
        Version version = new Version();
        version.setProvider(Publisher.PA);
        film.addVersion(version);
        
        setupContentResolver(film, expectedItemBrand, expectedItemSeries);

        progProcessor.process(inputProgData, channel, UTC, Timestamp.of(0));

        ArgumentCaptor<Item> itemArgCaptor = ArgumentCaptor.forClass(Item.class);
        verify(contentWriter, atLeastOnce()).createOrUpdate(itemArgCaptor.capture());

        ArgumentCaptor<Container> containerArgCaptor = ArgumentCaptor.forClass(Container.class);
        verify(contentWriter, atLeastOnce()).createOrUpdate(containerArgCaptor.capture());

        List<Container> writtenContainers = containerArgCaptor.getAllValues();

        Brand actualSummaryBrand = (Brand)writtenContainers.get(SUMMARY_BRAND_INDEX);
        Series actualSummarySeries = (Series)writtenContainers.get(SUMMARY_SERIES_INDEX); 

        assertEquals(expectedSummaryBrand, actualSummaryBrand);
        assertEquals(expectedSummarySeries, actualSummarySeries);

        assertThat(actualSummaryBrand.getEquivalentTo(), hasItem(expectedItemBrandLookupRef));
        assertThat(actualSummarySeries.getEquivalentTo(), hasItem(expectedItemSeriesLookupRef));
    }

    private ProgData setupProgData() {
        ProgData inputProgData = new ProgData();
        inputProgData.setProgId("1");
        inputProgData.setRtFilmnumber("5");
        inputProgData.setDuration("1");
        inputProgData.setDate("06/08/2012");
        inputProgData.setTime("11:40");
        Attr threeDAttr = new Attr();
        threeDAttr.setThreeD("true");
        inputProgData.setAttr(threeDAttr);
        inputProgData.setSeriesSummary("This is the series summary!");
        Season season = new Season();
        season.setSeasonSummary("This is the season summary!");
        inputProgData.setSeason(season);

        //PA Brand data
        inputProgData.setSeriesId("5");
        inputProgData.setTitle("My title");

        //PA Series data
        inputProgData.setSeriesId("5");
        inputProgData.setSeriesNumber("6");
        inputProgData.setEpisodeTotal("15");
        return inputProgData;
    }

    private void setupContentResolver(Film film, Brand brand, Series series) {
        when(contentResolver.findByCanonicalUris(ImmutableList.of("http://pressassociation.com/films/5")))
        .thenReturn(ResolvedContent.builder()
            .put("http://pressassociation.com/films/5", film)
            .build() 
        );
        when(contentResolver.findByCanonicalUris(ImmutableList.of("http://pressassociation.com/brands/5")))
        .thenReturn(ResolvedContent.builder()
            .put("http://pressassociation.com/brands/5", brand)
            .build() 
        );
        when(contentResolver.findByCanonicalUris(ImmutableList.of("http://pressassociation.com/series/5-6")))
        .thenReturn(ResolvedContent.builder()
            .put("http://pressassociation.com/series/5", series)
            .build() 
        );
    }

    @Test
    @Ignore
    public void testExtractsNewFilmWithEpisodeUri() {
        ProgData progData = new ProgData();
        progData.setProgId("1");
        progData.setRtFilmnumber("5");
        progData.setDuration("1");
        progData.setDate("06/08/2012");
        progData.setTime("11:40");
        when(contentResolver.findByCanonicalUris(ImmutableList.of(
            "http://pressassociation.com/films/5",
            "http://pressassociation.com/episodes/1"
        ))).thenReturn(ResolvedContent.builder().build());
        
        progProcessor.process(progData, channel, UTC, Timestamp.of(0));

        ArgumentCaptor<Item> argCaptor = ArgumentCaptor.forClass(Item.class);
        verify(contentWriter).createOrUpdate(argCaptor.capture());
        
        Item written = argCaptor.getValue();
        
        assertThat(written.getCanonicalUri(), is("http://pressassociation.com/episodes/1"));
        assertThat(written.getCurie(), is("pa:e-1"));

        // TODO new aliases
        assertThat(written.getAliasUrls(), hasItem("http://pressassociation.com/films/5"));
        
    }

    @Test
    @Ignore
    public void testAddsEpisodesAliasForFilmWithRtFilmNumberUri() {
        ProgData progData = new ProgData();
        progData.setProgId("1");
        progData.setRtFilmnumber("5");
        progData.setDuration("1");
        progData.setDate("06/08/2012");
        progData.setTime("11:40");
        
        Film film = new Film("http://pressassociation.com/films/5", "pa:f-5", Publisher.PA);
        Version version = new Version();
        version.setProvider(Publisher.PA);
        film.addVersion(version);
        
        when(contentResolver.findByCanonicalUris(ImmutableList.of(
            "http://pressassociation.com/films/5",
            "http://pressassociation.com/episodes/1"
        ))).thenReturn(ResolvedContent.builder()
            .put("http://pressassociation.com/films/5", film)
            .build() 
        );
        
        progProcessor.process(progData, channel, UTC, Timestamp.of(0));

        ArgumentCaptor<Item> argCaptor = ArgumentCaptor.forClass(Item.class);
        verify(contentWriter).createOrUpdate(argCaptor.capture());
        
        Item written = argCaptor.getValue();
        
        // TODO new aliases
        assertThat(written.getAliasUrls(), hasItem("http://pressassociation.com/episodes/1"));
        
    }

    @Test
    @Ignore
    public void testAddsRtFilmNumberAliasForFilmWithEpisodesUri() {
        ProgData progData = new ProgData();
        progData.setProgId("1");
        progData.setRtFilmnumber("5");
        progData.setDuration("1");
        progData.setDate("06/08/2012");
        progData.setTime("11:40");
        
        Film film = new Film("http://pressassociation.com/episodes/1", "pa:e-1", Publisher.PA);
        Version version = new Version();
        version.setProvider(Publisher.PA);
        film.addVersion(version);
        
        when(contentResolver.findByCanonicalUris(ImmutableList.of(
            "http://pressassociation.com/films/5",
            "http://pressassociation.com/episodes/1"
        ))).thenReturn(ResolvedContent.builder()
            .put("http://pressassociation.com/episodes/1", film)
            .build() 
        );
        
        progProcessor.process(progData, channel, UTC, Timestamp.of(0));

        ArgumentCaptor<Item> argCaptor = ArgumentCaptor.forClass(Item.class);
        verify(contentWriter).createOrUpdate(argCaptor.capture());
        
        Item written = argCaptor.getValue();
        
        // TODO new aliases
        assertThat(written.getAliasUrls(), hasItem("http://pressassociation.com/films/5"));
        
    }

}
