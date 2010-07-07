/* Copyright 2010 Meta Broadcast Ltd

Licensed under the Apache License, Version 2.0 (the "License"); you
may not use this file except in compliance with the License. You may
obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
implied. See the License for the specific language governing
permissions and limitations under the License. */

package org.uriplay.systest;

import java.util.Collection;
import java.util.Set;
import java.util.regex.Pattern;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.ModelAndView;
import org.uriplay.UriplayFetchModule;
import org.uriplay.equiv.EquivModule;
import org.uriplay.media.entity.Brand;
import org.uriplay.media.entity.Encoding;
import org.uriplay.media.entity.Equiv;
import org.uriplay.media.entity.Item;
import org.uriplay.media.entity.Location;
import org.uriplay.media.entity.Version;
import org.uriplay.persistence.MongoContentPersistenceModule;
import org.uriplay.persistence.equiv.EquivalentUrlStore;
import org.uriplay.query.QueryModule;
import org.uriplay.query.v2.QueryController;
import org.uriplay.remotesite.RemoteSiteModule;

import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import com.metabroadcast.common.persistence.MongoTestHelper;
import com.metabroadcast.common.servlet.StubHttpServletRequest;
import com.mongodb.Mongo;

public class EndToEndFetchTest extends TestCase {

	private static final String HULU_BRAND_URL = "http://www.hulu.com/glee";
	private static final String C4_BRAND_URL = "http://www.channel4.com/programmes/glee";
	private static final String BBC_BRAND_URL = "http://www.bbc.co.uk/programmes/b006m86d";
	private static final String WIKIPEDIA_URL = "http://en.wikipedia.org/glee";
	
	private ApplicationContext applicationContext = new AnnotationConfigApplicationContext(UriplayModelWithLocalMongo.class);
	
	public void testResolvingContent() throws Exception {
		
		EquivalentUrlStore equivStore = applicationContext.getBean(EquivalentUrlStore.class);

		equivStore.store(new Equiv(C4_BRAND_URL, WIKIPEDIA_URL));
		equivStore.store(new Equiv(C4_BRAND_URL, BBC_BRAND_URL));

		Brand c4Brand = queryForBrand(C4_BRAND_URL);
		
		// BBC Should not be an alias since it can be resolved with the BBC Brands adaptor
		assertEquals(Sets.newHashSet(WIKIPEDIA_URL, C4_BRAND_URL + "/4od"), c4Brand.getAliases());

		assertEquals(c4Brand, queryForBrand(WIKIPEDIA_URL));
		
		equivStore.store(new Equiv(HULU_BRAND_URL, WIKIPEDIA_URL));

		Brand huluBrand = queryForBrand(HULU_BRAND_URL);

		assertEquals(Sets.newHashSet(WIKIPEDIA_URL), huluBrand.getAliases());
		
		// check that the hulu brand has been merged with the C4 brand
		assertFalse(Iterables.isEmpty(Iterables.filter(locationUrisFrom(huluBrand), Predicates.contains(Pattern.compile("http://www.channel4.com.*")))));
		
	}

	private static Set<String> locationUrisFrom(Brand huluBrand) {
		Set<String> uris = Sets.newHashSet();
		for (Item item : huluBrand.getItems()) {
			for (Version version : item.getVersions()) {
				for (Encoding encoding : version.getManifestedAs()) {
					for (Location location : encoding.getAvailableAt()) {
						uris.add(location.getUri());
					}
				}
			}
		}
		return uris;
	}

	@SuppressWarnings("unchecked")
	private Brand queryForBrand(String uri) {
		QueryController queryController = applicationContext.getBean(QueryController.class);
		ModelAndView modelAndView = queryController.brand(new StubHttpServletRequest().withParam("uri", uri));
		Collection<Brand> brands = (Collection<Brand>) modelAndView.getModel().get("graph");
		return Iterables.getOnlyElement(brands);
	}

	@Configuration
	@ImportResource("classpath:uriplay.xml")
	@Import({EquivModule.class, QueryModule.class, MongoContentPersistenceModule.class, UriplayFetchModule.class, RemoteSiteModule.class})
	public static class UriplayModelWithLocalMongo {
		
		public @Bean Mongo mongo() {
			return MongoTestHelper.anEmptyMongo();
		}
	}
}
