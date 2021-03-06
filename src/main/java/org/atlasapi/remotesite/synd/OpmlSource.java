/* Copyright 2009 British Broadcasting Corporation
   Copyright 2009 Meta Broadcast Ltd

Licensed under the Apache License, Version 2.0 (the "License"); you
may not use this file except in compliance with the License. You may
obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
implied. See the License for the specific language governing
permissions and limitations under the License. */

package org.atlasapi.remotesite.synd;

import org.atlasapi.remotesite.BaseSource;

import com.sun.syndication.feed.opml.Opml;

/**
 * Object that wraps data fetched from an OPML outline feed
 * 
 * @author Robert Chatley (robert@metabroadcast.com)
 */
public class OpmlSource extends BaseSource {

	private final Opml opml;

	public OpmlSource(Opml opml, String uri) {
		super(uri);
		this.opml = opml;
	}

	public Opml getFeed() {
		return opml;
	}
	
	public String getTitle() {
		return opml.getTitle();
	}
}
