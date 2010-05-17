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


package org.uriplay.remotesite.bliptv;

import java.util.Set;

import org.uriplay.media.TransportType;
import org.uriplay.media.entity.Encoding;
import org.uriplay.media.entity.Item;
import org.uriplay.media.entity.Location;
import org.uriplay.media.entity.Version;
import org.uriplay.media.reference.entity.MimeType;
import org.uriplay.query.content.PerPublisherCurieExpander;
import org.uriplay.remotesite.ContentExtractor;
import org.uriplay.remotesite.html.HtmlDescriptionOfItem;
import org.uriplay.remotesite.html.HtmlDescriptionSource;

import com.google.common.collect.Sets;

public class BlipTvGraphExtractor implements ContentExtractor<HtmlDescriptionSource, Item>  {

	private static final String BLIP_TV_PUBLISHER = "blip.tv";

	public Item extract(HtmlDescriptionSource src) {
		
		Set<Encoding> encodings = Sets.newHashSet();
		
		boolean hasFLVEncoding = false;
		
		for (String locationUri : src.locationUris()) {

			Encoding encoding = encoding(locationUri);
			if (encoding == null) {
				// Location is not a recognised video encoding, it might be an mp3 file etc.
				continue;
			}
			
			if (locationUri.endsWith(".flv")) {
				encoding.addAvailableAt(flvDownloadLocationTo(locationUri));
				encoding.addAvailableAt(embedLocation(src.embedCode()));
				encoding.addAvailableAt(htmlLinkLocation(src.getUri()));
				hasFLVEncoding = true;
			} else {
				encoding.addAvailableAt(downloadLocation(src.getItem(), locationUri));
			}

			encodings.add(encoding);
		}
		
		if (!hasFLVEncoding) {
			Encoding encoding = new Encoding();
			encoding.addAvailableAt(htmlLinkLocation(src.getUri()));
			encodings.add(encoding);
		}
		
		Version version= new Version();
		version.setManifestedAs(encodings);
		
		String itemUri = src.getUri();
		Item item = item(itemUri,  src.getItem());
		item.addVersion(version);

		return item;
	}
	
	private Location htmlLinkLocation(String uri) {
		Location location = new Location();
		location.setUri(uri);
		location.setTransportType(TransportType.HTMLEMBED);
		return location;
	}

	private Location embedLocation(String embedCode) {
		Location location = new Location();
		location.setEmbedCode(embedCode);
		location.setTransportType(TransportType.EMBEDOBJECT);
		location.setTransportSubType("html");
		return location;
	}

	private Location flvDownloadLocationTo(String locationUri) {
		Location location = new Location();
		location.setUri(locationUri);
		location.setTransportType(TransportType.DOWNLOAD);
		return location;
	}

	private Item item(String itemUri, HtmlDescriptionOfItem htmlItem) {
		Item item = new Item();
		item.setCanonicalUri(itemUri);
		
		item.setTitle(htmlItem.getTitle());
		item.setDescription(htmlItem.getDescription());
		item.setPublisher(BLIP_TV_PUBLISHER);
		item.setThumbnail(htmlItem.getThumbnail());
		if (itemUri.startsWith("http://blip.tv/file/")) {
			item.setCurie(PerPublisherCurieExpander.CurieAlgorithm.BLIP.compact(itemUri));
		}
		return item;
	}
	
	private Encoding encoding( String locationUri) {
		MimeType dataContainerFormat = toDataContainerFormat(locationUri);
		if (dataContainerFormat == null) {
			return null;
		}
		Encoding encoding = new Encoding();
		encoding.setDataContainerFormat(dataContainerFormat);
		return encoding;
	}
	
	private static MimeType toDataContainerFormat(String locationUri) {
		if (locationUri.endsWith(".flv")) {
			return MimeType.VIDEO_XFLV;
		} else if (locationUri.endsWith(".mov")) {
			return MimeType.VIDEO_QUICKTIME;
		} else if (locationUri.endsWith(".m4v")) {
			return MimeType.VIDEO_MP4;
		} else if (locationUri.endsWith(".mp4")) {
			return MimeType.VIDEO_MP4;
		}
		return null;
	}
	
	private Location downloadLocation(HtmlDescriptionOfItem item, String locationUri) {
		Location location = new Location();
		location.setUri(locationUri);
		location.setTransportType(TransportType.DOWNLOAD);
		return location;
	}
}
