package org.atlasapi.output.simple;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.Currency;
import java.util.List;
import java.util.Set;

import org.atlasapi.application.ApplicationConfiguration;
import org.atlasapi.media.TransportSubType;
import org.atlasapi.media.TransportType;
import org.atlasapi.media.content.Actor;
import org.atlasapi.media.content.ContentGroupResolver;
import org.atlasapi.media.content.CrewMember;
import org.atlasapi.media.content.Encoding;
import org.atlasapi.media.content.Location;
import org.atlasapi.media.content.Policy;
import org.atlasapi.media.content.Publisher;
import org.atlasapi.media.content.Restriction;
import org.atlasapi.media.content.Version;
import org.atlasapi.media.content.Policy.RevenueContract;
import org.atlasapi.media.entity.simple.Item;
import org.atlasapi.media.product.ProductResolver;
import org.atlasapi.media.segment.SegmentResolver;
import org.atlasapi.media.topic.TopicQueryResolver;
import org.atlasapi.output.Annotation;
import org.atlasapi.persistence.output.ContainerSummaryResolver;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.metabroadcast.common.currency.Price;
import com.metabroadcast.common.intl.Countries;
import com.metabroadcast.common.media.MimeType;

public class ItemModelSimplifierTest {

    private final Mockery context = new Mockery();
    private final ContentGroupResolver contentGroupResolver = context.mock(ContentGroupResolver.class);
    private final TopicQueryResolver topicResolver = context.mock(TopicQueryResolver.class);
    private final SegmentResolver segmentResolver = context.mock(SegmentResolver.class);
    private final ProductResolver productResolver = context.mock(ProductResolver.class);
    private final ContainerSummaryResolver containerSummaryResolver = context.mock(ContainerSummaryResolver.class);
    
    private final ItemModelSimplifier itemSimplifier = new ItemModelSimplifier("localHostName", contentGroupResolver, topicResolver, productResolver, segmentResolver, containerSummaryResolver );
    
    @Test
    @SuppressWarnings("unchecked")
    public void testCanCreateSimpleItemFromFullItem() throws Exception {
        
        context.checking(new Expectations(){{
            allowing(contentGroupResolver).findByIds(with(any(Iterable.class)));will(returnValue(ImmutableList.of()));
            allowing(topicResolver).topicsForIds(with(any(Iterable.class)));will(returnValue(ImmutableList.of()));
            allowing(segmentResolver).resolveById(with(any(Iterable.class)));will(returnValue(ImmutableMap.of()));
        }});
        
        org.atlasapi.media.content.Item fullItem = new org.atlasapi.media.content.Item();
        Version version = new Version();
        
        Restriction restriction = new Restriction();
        restriction.setRestricted(true);
        restriction.setMessage("adults only");
        version.setRestriction(restriction);
        
        Encoding encoding = new Encoding();
        encoding.setDataContainerFormat(MimeType.VIDEO_3GPP);
        version.addManifestedAs(encoding);
        
        Location location = new Location();
        location.setUri("http://example.com");
        location.setPolicy(new Policy().withRevenueContract(RevenueContract.PAY_TO_BUY).withPrice(new Price(Currency.getInstance("GBP"), 99)).withAvailableCountries(Countries.GB));
        Location embed = new Location();
        embed.setTransportType(TransportType.EMBED);
        embed.setEmbedId("embedId");
        embed.setTransportSubType(TransportSubType.BRIGHTCOVE);
        
        encoding.addAvailableAt(location);
        encoding.addAvailableAt(embed);
        fullItem.addVersion(version);
        fullItem.setTitle("Collings and Herrin");
        
        CrewMember person = Actor.actor("hisID", "Andrew Collings", "Dirt-bag Humperdink", Publisher.BBC);
        fullItem.addPerson(person);
        
        Item simpleItem = itemSimplifier.simplify(fullItem, Annotation.defaultAnnotations(), ApplicationConfiguration.DEFAULT_CONFIGURATION);
        List<org.atlasapi.media.entity.simple.Person> people = simpleItem.getPeople();
        org.atlasapi.media.entity.simple.Person simpleActor = Iterables.getOnlyElement(people);
        assertThat(simpleActor.getCharacter(), is("Dirt-bag Humperdink"));
        assertThat(simpleActor.getName(), is("Andrew Collings"));
        
        Set<org.atlasapi.media.entity.simple.Location> simpleLocations = simpleItem.getLocations();
        assertThat(simpleLocations.size(), is(2));
        org.atlasapi.media.entity.simple.Location simpleLocation = Iterables.getFirst(simpleLocations, null);
        
        assertThat(simpleLocation.getUri(), is("http://example.com"));
        assertThat(simpleLocation.getDataContainerFormat(), is(MimeType.VIDEO_3GPP.toString()));
        assertThat(simpleLocation.getRestriction().getMessage(), is("adults only"));
        assertThat(simpleLocation.getRevenueContract(), is("pay_to_buy"));
        assertThat(simpleLocation.getCurrency(), is("GBP"));
        assertThat(simpleLocation.getPrice(), is(99));
        assertThat(simpleLocation.getAvailableCountries().size(), is(1));
        assertThat(simpleLocation.getAvailableCountries().iterator().next(), is("GB"));
        
        org.atlasapi.media.entity.simple.Location simpleEmbed = Iterables.getLast(simpleLocations, null);
        assertThat(simpleEmbed.getEmbedId(), is("embedId"));
        assertThat(simpleEmbed.getTransportType(), is("embed"));
        assertThat(simpleEmbed.getTransportSubType(), is("brightcove"));
        
        assertThat(simpleItem.getTitle(), is("Collings and Herrin"));
    }

}
