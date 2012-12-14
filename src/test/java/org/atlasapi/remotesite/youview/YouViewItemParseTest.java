package org.atlasapi.remotesite.youview;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.Map;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.ParsingException;
import nu.xom.ValidityException;

import org.atlasapi.media.channel.Channel;
import org.atlasapi.media.entity.Broadcast;
import org.atlasapi.media.entity.Encoding;
import org.atlasapi.media.entity.Item;
import org.atlasapi.media.entity.Location;
import org.atlasapi.media.entity.MediaType;
import org.atlasapi.media.entity.Policy.Platform;
import org.atlasapi.media.entity.Publisher;
import org.atlasapi.media.entity.Version;
import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.metabroadcast.common.intl.Countries;

public class YouViewItemParseTest {

    @SuppressWarnings("deprecation")
    private final Channel FIVE = new Channel(Publisher.METABROADCAST, "Channel 5", "five", false, MediaType.VIDEO, "http://www.five.tv");
    
    private final YouViewContentExtractor contentExtractor;
    
    public YouViewItemParseTest() {
        Map<Integer, Channel> channelMapping = ImmutableMap.<Integer, Channel>builder()
                .put(1044, FIVE)
                .build();
        contentExtractor = new YouViewContentExtractor(new DummyYouViewChannelResolver(channelMapping));
    }
    
    @Test
    public void testItemParsing() throws ValidityException, ParsingException, IOException {
        Item item = contentExtractor.extract(getContentElementFromFile("youview-item.xml"));
        
        assertEquals("http://youview.com/scheduleevent/7780297", item.getCanonicalUri());
        assertEquals("Hatfields & McCoys", item.getTitle());
        assertEquals(MediaType.VIDEO, item.getMediaType());
        assertEquals(ImmutableSet.of("7655992", "crid://www.five.tv/V65K2"), item.getAliases());
        assertEquals(Publisher.YOUVIEW, item.getPublisher());
        
        Version version = Iterables.getOnlyElement(item.getVersions());
        assertThat(version.getPublishedDuration(), is(3600));
        
        Broadcast broadcast = Iterables.getOnlyElement(version.getBroadcasts());
        
        assertEquals("http://www.five.tv", broadcast.getBroadcastOn());
        assertEquals(new DateTime(2012, 11, 18, 23, 30, 00), broadcast.getTransmissionTime());
        assertEquals(new DateTime(2012, 11, 19, 00, 30, 00), broadcast.getTransmissionEndTime());
        assertThat(broadcast.getBroadcastDuration(), is(3600));
        assertEquals(ImmutableSet.of("dvb://233a..2134;8696"), broadcast.getAliases());
        assertEquals("youview:7780297", broadcast.getSourceId());
        
        Encoding encoding = Iterables.getOnlyElement(version.getManifestedAs());
        Location location = Iterables.getOnlyElement(encoding.getAvailableAt());
        assertEquals(new DateTime(2012, 11, 15, 21, 00, 00), location.getPolicy().getAvailabilityStart());
        assertEquals(new DateTime(2012, 12, 15, 21, 00, 00), location.getPolicy().getAvailabilityEnd());
        assertEquals(Platform.YOUVIEW, location.getPolicy().getPlatform());
        assertEquals(ImmutableSet.of(Countries.GB), location.getPolicy().getAvailableCountries());
    }
    

    
    @Test
    public void testItemParsingNoProgrammeId() throws ValidityException, ParsingException, IOException {
        Item item = contentExtractor.extract(getContentElementFromFile("youview-item-no-programme-id.xml"));
        
        assertEquals(ImmutableSet.of("crid://www.five.tv/V65K2"), item.getAliases());
    }
    
    public static Element getContentElementFromFile(String fileName) throws ValidityException, ParsingException, IOException {
        Document youViewData = new Builder().build(new ClassPathResource(fileName).getInputStream());
        Element root = youViewData.getRootElement();
        String namespace = root.getNamespaceURI();
        return root.getFirstChildElement("entry", namespace);
    }

}