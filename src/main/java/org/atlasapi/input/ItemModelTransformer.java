package org.atlasapi.input;

import java.util.Currency;
import java.util.Date;
import java.util.Set;

import org.atlasapi.media.TransportSubType;
import org.atlasapi.media.TransportType;
import org.atlasapi.media.entity.Encoding;
import org.atlasapi.media.entity.Episode;
import org.atlasapi.media.entity.Film;
import org.atlasapi.media.entity.Item;
import org.atlasapi.media.entity.ParentRef;
import org.atlasapi.media.entity.Policy;
import org.atlasapi.media.entity.Policy.Platform;
import org.atlasapi.media.entity.Policy.RevenueContract;
import org.atlasapi.media.entity.Song;
import org.atlasapi.media.entity.Version;
import org.atlasapi.media.entity.Location;
import org.atlasapi.persistence.content.ContentResolver;
import org.atlasapi.persistence.topic.TopicStore;
import org.joda.time.DateTime;
import org.joda.time.Duration;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;
import com.metabroadcast.common.currency.Price;
import com.metabroadcast.common.intl.Countries;
import com.metabroadcast.common.media.MimeType;
import com.metabroadcast.common.time.DateTimeZones;

public class ItemModelTransformer extends ContentModelTransformer<org.atlasapi.media.entity.simple.Item, Item> {

    public ItemModelTransformer(ContentResolver resolver, TopicStore topicStore) {
        super(resolver, topicStore);
    }

    @Override
    protected Item createOutput(org.atlasapi.media.entity.simple.Item inputItem) {
        String type = inputItem.getType();
        Item item;
        if ("episode".equals(type)) {
            item = createEpisode(inputItem);
        } else if ("film".equals(type)) {
            Film film = new Film();
            item = film;
        } else if ("song".equals(type)) {
            item = createSong(inputItem);
        } else {
            item = new Item();
        }
        return setItemFields(item, inputItem);
    }

    protected Item createSong(org.atlasapi.media.entity.simple.Item inputItem) {
        Song song = new Song();
        song.setIsrc(inputItem.getIsrc());
        if (inputItem.getDuration() != null) {
            song.setDuration(Duration.standardSeconds(inputItem.getDuration()));
        }
        return song;
    }
    

    private Item createEpisode(org.atlasapi.media.entity.simple.Item inputItem) {
        Episode episode = new Episode();
        episode.setSeriesNumber(inputItem.getSeriesNumber());
        episode.setEpisodeNumber(inputItem.getEpisodeNumber());
        if (inputItem.getSeriesSummary() != null) {
            episode.setSeriesRef(new ParentRef(inputItem.getSeriesSummary().getUri()));
        }
        return episode;
    }

    protected Item setItemFields(Item item, org.atlasapi.media.entity.simple.Item inputItem) {
        Set<Encoding> encodings = encodingsFrom(inputItem.getLocations());
        if (!encodings.isEmpty()) {
            Version version = new Version();
            version.setManifestedAs(encodings);
            item.setVersions(ImmutableSet.of(version));
        }
        return item;
    }

    private Set<Encoding> encodingsFrom(Set<org.atlasapi.media.entity.simple.Location> locations) {
        Builder<Encoding> encodings = ImmutableSet.builder();
        for (org.atlasapi.media.entity.simple.Location simpleLocation : locations) {
            Encoding encoding = encodingFrom(simpleLocation);
            Location location = locationFrom(simpleLocation);
            Policy policy = policyFrom(simpleLocation);
            location.setPolicy(policy);
            encoding.setAvailableAt(ImmutableSet.of(location));
            encodings.add(encoding);
        }
        return encodings.build();
    }

    private Encoding encodingFrom(org.atlasapi.media.entity.simple.Location inputLocation) {
        Encoding encoding = new Encoding();
        encoding.setAdvertisingDuration(inputLocation.getAdvertisingDuration());
        encoding.setAudioBitRate(inputLocation.getAudioBitRate());
        encoding.setAudioChannels(inputLocation.getAudioChannels());
        encoding.setAudioCoding(asMimeType(inputLocation.getAudioCoding()));
        encoding.setBitRate(inputLocation.getBitRate());
        encoding.setContainsAdvertising(inputLocation.getContainsAdvertising());
        encoding.setDataContainerFormat(asMimeType(inputLocation.getDataContainerFormat()));
        encoding.setDataSize(inputLocation.getDataSize());
        encoding.setDistributor(inputLocation.getDistributor());
        encoding.setHasDOG(inputLocation.getHasDOG());
        encoding.setSource(inputLocation.getSource());
        encoding.setVideoAspectRatio(inputLocation.getVideoAspectRatio());
        encoding.setVideoBitRate(inputLocation.getVideoBitRate());
        encoding.setVideoCoding(asMimeType(inputLocation.getVideoCoding()));
        encoding.setVideoFrameRate(inputLocation.getVideoFrameRate());
        encoding.setVideoHorizontalSize(inputLocation.getVideoHorizontalSize());
        encoding.setVideoProgressiveScan(inputLocation.getVideoProgressiveScan());
        encoding.setVideoVerticalSize(inputLocation.getVideoVerticalSize());
        return encoding;
    }

    private Location locationFrom(org.atlasapi.media.entity.simple.Location inputLocation) {
        Location location = new Location();
        location.setEmbedCode(inputLocation.getEmbedCode());
        location.setEmbedId(inputLocation.getEmbedId());
        location.setTransportIsLive(inputLocation.getTransportIsLive());
        
        if (inputLocation.getTransportSubType() != null) {
            location.setTransportSubType(TransportSubType.fromString(inputLocation.getTransportSubType()));
        }
        if (inputLocation.getTransportType() != null) {
            location.setTransportType(TransportType.fromString(inputLocation.getTransportType()));
        }
        return location;
    }

    private Policy policyFrom(org.atlasapi.media.entity.simple.Location inputLocation) {
        Policy policy = new Policy();
        policy.setAvailabilityStart(asUtcDateTime(inputLocation.getAvailabilityStart()));
        policy.setAvailabilityEnd(asUtcDateTime(inputLocation.getAvailabilityEnd()));
        policy.setDrmPlayableFrom(asUtcDateTime(inputLocation.getDrmPlayableFrom()));
        policy.setPlatform(Platform.fromKey(inputLocation.getPlatform()));
        if (inputLocation.getCurrency() != null && inputLocation.getPrice() != null) {
            Currency currency = Currency.getInstance(inputLocation.getCurrency());
            policy.setPrice(new Price(currency, inputLocation.getPrice()));
        }
        if (inputLocation.getAvailableCountries() != null) {
            policy.setAvailableCountries(Countries.fromCodes(inputLocation.getAvailableCountries()));
        }
        policy.setRevenueContract(RevenueContract.fromKey(inputLocation.getRevenueContract()));
        return policy;
    }
    
    private MimeType asMimeType(String mimeType) {
        if (mimeType != null) {
            return MimeType.fromString(mimeType);
        }
        return null;
    }

    private DateTime asUtcDateTime(Date date) {
        if (date != null) {
            return null;
        }
        return new DateTime(date).withZone(DateTimeZones.UTC);
    }

}
