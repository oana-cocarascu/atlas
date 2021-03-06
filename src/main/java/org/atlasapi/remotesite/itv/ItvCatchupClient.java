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

package org.atlasapi.remotesite.itv;

import java.io.Reader;
import java.io.StringReader;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.atlasapi.persistence.system.RemoteSiteClient;
import org.atlasapi.remotesite.FetchException;
import org.atlasapi.remotesite.HttpClients;
import org.atlasapi.remotesite.html.HtmlNavigator;
import org.jdom.Element;

import com.metabroadcast.common.http.SimpleHttpClient;

/**
 * Client to retrieve XML/HTML from ITV and bind it to our object model. 
 *  
 * @author Robert Chatley (robert@metabroadcast.com)
 * @author John Ayres (john@metabroadcast.com)
 */
public class ItvCatchupClient implements RemoteSiteClient<List<ItvProgramme>> {

	private final SimpleHttpClient httpClient;
	private final JAXBContext context;

	private static final Log log = LogFactory.getLog(ItvCatchupClient.class);
	
	public ItvCatchupClient() {
		this(HttpClients.screenScrapingClient());
	}
	
	public ItvCatchupClient(SimpleHttpClient httpClient) {
		this.httpClient = httpClient;
		try {
			context = JAXBContext.newInstance(ItvProgrammes.class, ItvProgramme.class);
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	public List<ItvProgramme> get(String uri) throws Exception {
		Reader in = new StringReader(httpClient.getContentsOf(uri));
		Unmarshaller u = context.createUnmarshaller();
		u.setSchema(null);
		ItvProgrammes itvProgrammes = (ItvProgrammes) u.unmarshal(in);
		for (ItvProgramme program : itvProgrammes.programmeList()) {
			log.info("fetching details for: " + program.url());
			fetchEpisodeDetails(program);
		}
		return itvProgrammes.programmeList();
	}

	private void fetchEpisodeDetails(ItvProgramme program) {

		int programmeId = program.programmeId();
		
		try {
			String data = httpClient.getContentsOf("http://www.itv.com/_app/Dynamic/CatchUpData.ashx?ViewType=1&Filter=" + programmeId + "&moduleID=262033&columnWidth=2");
			HtmlNavigator htmlNavigator = new HtmlNavigator(data);
			List<Element> content = htmlNavigator.allElementsMatching("//div[@class='content']");
			
			addEpisodesTo(program, htmlNavigator, content);
			
		} catch (Exception e) {
			throw new FetchException("Error fetching ITV programme with id: " + programmeId, e);
		}
		
	}

	private void addEpisodesTo(ItvProgramme program, HtmlNavigator htmlNavigator, List<Element> content) {
	
		for (Element element : content) {
			
			Element link = htmlNavigator.firstElementOrNull("./h3/a", element);
			String episodePage = link.getAttributeValue("href");
			Element description = htmlNavigator.firstElementOrNull("./p[@class='progDesc']", element);
			Element date = htmlNavigator.firstElementOrNull("./p[@class='date']", element);
			
			program.addEpisode(new ItvEpisode(date.getText(), description.getText(), episodePage));
		}
	}

}
