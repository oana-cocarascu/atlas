/* Copyright 2009 Meta Broadcast Ltd

Licensed under the Apache License, Version 2.0 (the "License"); you
may not use this file except in compliance with the License. You may
obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
implied. See the License for the specific language governing
permissions and limitations under the License. */

package org.atlasapi.remotesite.channel4;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.atlasapi.media.TransportType;
import org.atlasapi.media.entity.Broadcast;
import org.atlasapi.media.entity.Content;
import org.atlasapi.media.entity.Countries;
import org.atlasapi.media.entity.Country;
import org.atlasapi.media.entity.Encoding;
import org.atlasapi.media.entity.Episode;
import org.atlasapi.media.entity.Location;
import org.atlasapi.media.entity.Policy;
import org.atlasapi.media.entity.Publisher;
import org.atlasapi.media.entity.Version;
import org.atlasapi.persistence.content.ContentResolver;
import org.atlasapi.persistence.logging.AdapterLog;
import org.atlasapi.persistence.logging.AdapterLogEntry;
import org.atlasapi.persistence.logging.AdapterLogEntry.Severity;
import org.atlasapi.query.content.PerPublisherCurieExpander;
import org.atlasapi.remotesite.ContentExtractor;
import org.jdom.Attribute;
import org.jdom.Element;
import org.jdom.Namespace;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.LocalDate;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.metabroadcast.common.time.DateTimeZones;
import com.sun.syndication.feed.atom.Entry;
import com.sun.syndication.feed.atom.Feed;
import com.sun.syndication.feed.atom.Link;

public class C4EpisodesExtractor implements ContentExtractor<Feed, List<Episode>> {
    
	public static final String DC_GUIDANCE = "dc:relation.Guidance";
	public static final String DC_TERMS_AVAILABLE = "dcterms:available";
	public static final String DC_TX_DATE = "dc:date.TXDate";
	public static final String DC_START_TIME = "dc:relation.TXStartTime";
	public static final String DC_TX_CHANNEL = "dc:relation.TXChannel";
	public static final String EPISODE_TITLE_TEMPLATE = "Series %s Episode %s";
	public static final String DC_EPISODE_NUMBER = "dc:relation.EpisodeNumber";
	public static final String DC_SERIES_NUMBER = "dc:relation.SeriesNumber";

	private static final Namespace NS_MEDIA_RSS = Namespace.getNamespace("http://search.yahoo.com/mrss/");
	
	private static final Pattern AVAILABILTY_RANGE_PATTERN = Pattern.compile("start=(\\d{4}-\\d{2}-\\d{2}); end=(\\d{4}-\\d{2}-\\d{2}); scheme=W3C-DTF");

	public static Map<String, String> CHANNEL_LOOKUP = channelLookup();
	private final AdapterLog log;
    private final ContentResolver contentResolver; 
	
	public C4EpisodesExtractor(ContentResolver contentResolver, AdapterLog log) {
		this.contentResolver = contentResolver;
        this.log = log;
	}
	
	private static Map<String, String> channelLookup() {
		Map<String, String> channelLookup = Maps.newHashMap();
		channelLookup.put("C4", "http://www.channel4.com");
		channelLookup.put("M4", "http://www.channel4.com/more4");
		channelLookup.put("E4", "http://www.e4.com");
		return channelLookup;
	}
	
	private boolean include4odInfo = false;
	private boolean inlcudeBroadcasts = false;
	
	public C4EpisodesExtractor includeOnDemands() {
		this.include4odInfo = true;
		return this;
	}
	
	public C4EpisodesExtractor includeBroadcasts() {
		this.inlcudeBroadcasts = true;
		return this;
	}
	
	private static final Pattern SERIES_AND_EPISODE_NUMBER = Pattern.compile("series-(\\d+)/episode-(\\d+)");
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Episode> extract(Feed source) {
		
		String feedTitle = Strings.nullToEmpty(source.getTitle());
		
		List<Episode> episodes = Lists.newArrayList();
		
		for (Entry entry : (List<Entry>) source.getEntries()) {
			Map<String, String> lookup = C4AtomApi.foreignElementLookup(entry);
			
			Integer seriesNumber = C4AtomApi.readAsNumber(lookup, DC_SERIES_NUMBER);
			Integer episodeNumber = C4AtomApi.readAsNumber(lookup, DC_EPISODE_NUMBER);
			
			String itemUri = C4AtomApi.canonicalUri(entry);
			
			if (itemUri == null) {
				// fall back to hacking the uri out of the feed
				itemUri = extarctUriFromLink(source, entry);
			}
			if (itemUri == null) {
			    log.record(new AdapterLogEntry(Severity.WARN).withDescription("Unable to derive URI for c4 episode with id: " + entry.getId()).withSource(this.getClass()));
			    continue;
			}
			
			Episode episode = new Episode(itemUri, PerPublisherCurieExpander.CurieAlgorithm.C4.compact(itemUri), Publisher.C4);

			String fourOdUri = C4AtomApi.fourOdUri(entry);
			if (fourOdUri != null) {
				episode.addAlias(fourOdUri);
			}
			
			episode.setLastUpdated(new DateTime(source.getUpdated(), DateTimeZones.UTC));
			
			if (C4AtomApi.isAnEpisodeId(entry.getId())) {
				episode.addAlias(entry.getId());
			}

			episode.setTitle(title(entry));
			
			if ((Strings.isNullOrEmpty(episode.getTitle()) || feedTitle.startsWith(episode.getTitle())) && episodeNumber != null && seriesNumber != null) {
				episode.setTitle(String.format(EPISODE_TITLE_TEMPLATE , seriesNumber, episodeNumber));
			}
			
			episode.setEpisodeNumber(episodeNumber);
			episode.setSeriesNumber(seriesNumber);
			
			Element mediaGroup = mediaGroup(entry);
			
			Set<Country> availableCountries = null;
			
			if (mediaGroup != null) {
				Element thumbnail = mediaGroup.getChild("thumbnail", NS_MEDIA_RSS);
				if (thumbnail != null) {
					Attribute thumbnailUri = thumbnail.getAttribute("url");
					C4AtomApi.addImages(episode, thumbnailUri.getValue());
				}
				Element restriction = mediaGroup.getChild("restriction", NS_MEDIA_RSS);
				if (restriction != null && restriction.getValue() != null) {
					availableCountries = Countries.fromDelimtedList(restriction.getValue());
				}
			}
			
			episode.setIsLongForm(true);
			episode.setDescription(description(entry));
			
			Episode oldEpisode = null;
			if (contentResolver != null && (include4odInfo || inlcudeBroadcasts)) {
			    Content oldContent = contentResolver.findByUri(episode.getCanonicalUri());
			    if (oldContent instanceof Episode) {
			        oldEpisode = (Episode) oldContent;
			    }
			}
			
			Version version = version(C4AtomApi.fourOdUri(entry), entry.getId(), lookup, availableCountries, new DateTime(entry.getUpdated(), DateTimeZones.UTC), oldEpisode);
			if (version != null) {
				episode.addVersion(version);
			}
			episodes.add(episode);
		}
		return episodes;
	}

	@SuppressWarnings("unchecked")
	private String extarctUriFromLink(Feed source, Entry entry) {
		for (Link link : (List<Link>) entry.getOtherLinks()) {
			Matcher matcher = SERIES_AND_EPISODE_NUMBER.matcher(link.getHref());
			if (matcher.find()) {
				return C4AtomApi.episodeUri(C4AtomApi.webSafeNameFromAnyFeedId(source.getId()), Integer.valueOf(matcher.group(1)), Integer.valueOf(matcher.group(2)));	
			}
		}
		return null;
	}

	
	
	private String description(Entry entry) {
		com.sun.syndication.feed.atom.Content description = entry.getSummary();
		if (description == null) {
			return null;
		}
		return description.getValue();
	}
	
	private String title(Entry entry) {
        com.sun.syndication.feed.atom.Content title = entry.getTitleEx();
        if (title == null) {
            return null;
        }
        return title.getValue();
    }
	
	private Location location(String uri, String locationId, Map<String, String> lookup, Set<Country> availableCountries, DateTime lastUpdated) {
		Location location = new Location();
		location.setUri(uri);
		location.addAlias(locationId);
		location.setTransportType(TransportType.LINK);
		location.setLastUpdated(lastUpdated);
		
		// The feed only contains available content
		location.setAvailable(true);
		
		String availability = lookup.get(DC_TERMS_AVAILABLE);
		
		if (availability != null) {
			Matcher matcher = AVAILABILTY_RANGE_PATTERN.matcher(availability);
			if (matcher.matches()) {
				Policy policy = new Policy()
					.withAvailabilityStart(new DateTime(matcher.group(1)))
					.withAvailabilityEnd(new LocalDate(matcher.group(2)).plusDays(1).toDateTimeAtStartOfDay());
					
				if (availableCountries != null) {
					policy.setAvailableCountries(availableCountries);
				}
				location.setPolicy(policy);
			}
		}
		return location;
	}


	private Version version(String uri, String locationId, Map<String, String> lookup, Set<Country> availableCountries, DateTime lastUpdated, Episode oldEpisode) {
		Version version = new Version();
		Duration duration = C4AtomApi.durationFrom(lookup);
		
		if (duration != null) {
			version.setDuration(duration);
		}
		
		String guidance = lookup.get(DC_GUIDANCE);
		if (guidance != null) {
			version.setRating("http://ref.atlasapi.org/ratings/simple/adult");
			version.setRatingText(guidance);
		} else {
			version.setRating("http://ref.atlasapi.org/ratings/simple/nonadult");
		} 
		
		if (inlcudeBroadcasts) {
			String txChannel = CHANNEL_LOOKUP.get(lookup.get(DC_TX_CHANNEL));
			String txDate = lookup.get(DC_TX_DATE);
			if (txChannel != null) {
				DateTime txStart = new DateTime(txDate);
				Broadcast broadcast = new Broadcast(txChannel, txStart, duration);
				broadcast.setLastUpdated(lastUpdated);
				version.addBroadcast(broadcast);
			}
			
			addInactiveBroadcasts(version, oldEpisode);
		}
		
		if (include4odInfo) {
			Encoding encoding = new Encoding();
			encoding.addAvailableAt(location(uri, locationId, lookup, availableCountries, lastUpdated));
			version.addManifestedAs(encoding);
		}
		addUnavailableLocations(version, oldEpisode);
				
		return version.getBroadcasts().isEmpty() && version.getManifestedAs().isEmpty() ? null : version;
	}

	private void addInactiveBroadcasts(Version version, Episode oldEpisode) {
	    if (oldEpisode != null) {
    	    for (Version oldVersion: oldEpisode.getVersions()) {
    	        for (Broadcast broadcast: oldVersion.getBroadcasts()) {
    	            if (! version.getBroadcasts().contains(broadcast)) {
    	                if (broadcast.isActivelyPublished()) {
    	                    broadcast.setLastUpdated(new DateTime(DateTimeZones.UTC));
    	                }
    	                broadcast.setIsActivelyPublished(Boolean.FALSE);
    	                version.addBroadcast(broadcast);
    	            }
    	        }
    	    }
	    }
	}
	
	private void addUnavailableLocations(Version version, Episode oldEpisode) {
	    if (oldEpisode != null) {
    	    if (version.getManifestedAs().isEmpty() && !oldEpisode.getVersions().isEmpty()) {
    	        Version oldVersion = oldEpisode.getVersions().iterator().next();
    	        version.setManifestedAs(oldVersion.getManifestedAs());
    	        
    	        for (Encoding encoding: version.getManifestedAs()) {
    	            for (Location location: encoding.getAvailableAt()) {
    	                if (location.getAvailable()) {
    	                    location.setLastUpdated(new DateTime(DateTimeZones.UTC));
    	                }
    	                location.setAvailable(false);
    	            }
    	        }
    	    }
	    }
    }
	

	@SuppressWarnings("unchecked")
	private Element mediaGroup(Entry syndEntry) {
		for (Element element : (List<Element>) syndEntry.getForeignMarkup()) {
			if (NS_MEDIA_RSS.equals(element.getNamespace()) && "group".equals(element.getName())) {
				return element;
			}
		}
		return null;
	}
}
