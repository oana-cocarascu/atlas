package org.atlasapi.output.simple;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.atlasapi.application.ApplicationConfiguration;
import org.atlasapi.media.entity.Certificate;
import org.atlasapi.media.entity.Clip;
import org.atlasapi.media.common.Id;
import org.atlasapi.media.content.Content;
import org.atlasapi.media.entity.ContentGroup;
import org.atlasapi.media.entity.ContentGroupRef;
import org.atlasapi.media.entity.CrewMember;
import org.atlasapi.media.entity.Identified;
import org.atlasapi.media.entity.Item;
import org.atlasapi.media.topic.Topic;
import org.atlasapi.media.entity.TopicRef;
import org.atlasapi.media.entity.simple.Description;
import org.atlasapi.media.entity.simple.KeyPhrase;
import org.atlasapi.media.entity.simple.Language;
import org.atlasapi.media.entity.simple.RelatedLink;
import org.atlasapi.media.product.Product;
import org.atlasapi.output.Annotation;
import org.atlasapi.persistence.content.ContentGroupResolver;
import org.atlasapi.persistence.media.product.ProductResolver;
import org.atlasapi.persistence.topic.TopicQueryResolver;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public abstract class ContentModelSimplifier<F extends Content, T extends Description> extends DescribedModelSimplifier<F, T> {

    private final ContentGroupResolver contentGroupResolver;
    private final ModelSimplifier<ContentGroup, org.atlasapi.media.entity.simple.ContentGroup> contentGroupSimplifier;
    private final TopicQueryResolver topicResolver;
    private final ModelSimplifier<Topic, org.atlasapi.media.entity.simple.Topic> topicSimplifier;
    private final ProductResolver productResolver;
    private final ModelSimplifier<Product, org.atlasapi.media.entity.simple.Product> productSimplifier;
    protected final CrewMemberSimplifier crewSimplifier = new CrewMemberSimplifier();
    private boolean exposeIds = false;
    private final Map<String, Locale> localeMap;

    public ContentModelSimplifier(String localHostName, ContentGroupResolver contentGroupResolver, TopicQueryResolver topicResolver, ProductResolver productResolver) {
        this.contentGroupResolver = contentGroupResolver;
        this.topicResolver = topicResolver;
        this.productResolver = productResolver;
        this.contentGroupSimplifier = new ContentGroupModelSimplifier();
        this.topicSimplifier = new TopicModelSimplifier(localHostName);
        this.productSimplifier = new ProductModelSimplifier(localHostName);
        this.localeMap = initLocalMap();
    }

    private Map<String, Locale> initLocalMap() {
        ImmutableMap.Builder<String, Locale> builder = ImmutableMap.builder();
        for (String code : Locale.getISOLanguages()) {
            builder.put(code, new Locale(code));
        }
        return builder.build();
    }


    protected void copyBasicContentAttributes(F content, T simpleDescription, final Set<Annotation> annotations, final ApplicationConfiguration config) {
        copyBasicDescribedAttributes(content, simpleDescription, annotations);

        if(!exposeIds) {
            simpleDescription.setId(null);
        }
        
        if (annotations.contains(Annotation.DESCRIPTION) || annotations.contains(Annotation.EXTENDED_DESCRIPTION)) {
            simpleDescription.setYear(content.getYear());
            if (annotations.contains(Annotation.EXTENDED_DESCRIPTION)) {
                simpleDescription.setOriginalLanguages(languagesFrom(content.getLanguages()));
                simpleDescription.setCertificates(simpleCertificates(content.getCertificates()));
            }
        }
        
        if (annotations.contains(Annotation.CLIPS)) {
            simpleDescription.setClips(clipToSimple(content.getClips(), annotations, config));
        }
        if (annotations.contains(Annotation.TOPICS)) {
            simpleDescription.setTopics(topicRefToSimple(content, content.getTopicRefs(), annotations, config));
        }
        if (annotations.contains(Annotation.CONTENT_GROUPS)) {
            simpleDescription.setContentGroups(contentGroupRefToSimple(content.getContentGroupRefs(), annotations, config));
        }
        if (annotations.contains(Annotation.KEY_PHRASES)) {
            simpleDescription.setKeyPhrases(simplifyPhrases(content));
        }
        if (annotations.contains(Annotation.RELATED_LINKS)) {
            simpleDescription.setRelatedLinks(simplifyRelatedLinks(content));
        }
        if (annotations.contains(Annotation.PRODUCTS)) {
            simpleDescription.setProducts(resolveAndSimplifyProductsFor(content, annotations, config));
        }
        
        if (annotations.contains(Annotation.PEOPLE)) {
            simpleDescription.setPeople(Iterables.filter(Iterables.transform(content.people(), new Function<CrewMember, org.atlasapi.media.entity.simple.Person>() {

                @Override
                public org.atlasapi.media.entity.simple.Person apply(CrewMember input) {
                    return crewSimplifier.simplify(input, annotations, config);
                }
            }), Predicates.notNull()));
        }
    }
    
    private Iterable<org.atlasapi.media.entity.simple.Certificate> simpleCertificates(Set<Certificate> certificates) {
        return Iterables.transform(certificates, new Function<Certificate, org.atlasapi.media.entity.simple.Certificate>() {

            @Override
            public org.atlasapi.media.entity.simple.Certificate apply(Certificate input) {
                return new org.atlasapi.media.entity.simple.Certificate(input.classification(), input.country().code());
            }
        });
    }
    
    protected Language languageForCode(String input) {
        Locale locale = localeMap.get(input);
        if (locale == null) {
            return null;
        }
        return new Language(locale.getLanguage(), locale.getDisplayLanguage());
    }
    
    private Iterable<Language> languagesFrom(Set<String> languages) {
        return Iterables.filter(Iterables.transform(languages, new Function<String, Language>() {

            @Override
            public Language apply(String input) {
                return languageForCode(input);
            }
        }), Predicates.notNull());
    }

    private Iterable<org.atlasapi.media.entity.simple.Product> resolveAndSimplifyProductsFor(Content content, final Set<Annotation> annotations, final ApplicationConfiguration config) {
        return Iterables.transform(filter(productResolver.productsForContent(content.getCanonicalUri()), config), new Function<Product, org.atlasapi.media.entity.simple.Product>() {

            @Override
            public org.atlasapi.media.entity.simple.Product apply(Product input) {
                return productSimplifier.simplify(input, annotations, config);
            }
        });
    }

    private Iterable<Product> filter(Iterable<Product> productsForContent, final ApplicationConfiguration config) {
        return Iterables.filter(productsForContent, new Predicate<Product>() {

            @Override
            public boolean apply(Product input) {
                return config.isEnabled(input.getPublisher());
            }
        });
    }
    
    public void exposeIds(boolean expose) {
        this.exposeIds = expose;
    }

    public Iterable<RelatedLink> simplifyRelatedLinks(F content) {
        return Iterables.transform(content.getRelatedLinks(), new Function<org.atlasapi.media.entity.RelatedLink, RelatedLink>() {

            @Override
            public RelatedLink apply(org.atlasapi.media.entity.RelatedLink rl) {
                RelatedLink simpleLink = new RelatedLink();

                simpleLink.setUrl(rl.getUrl());
                simpleLink.setType(rl.getType().toString().toLowerCase());
                simpleLink.setSourceId(rl.getSourceId());
                simpleLink.setShortName(rl.getShortName());
                simpleLink.setTitle(rl.getTitle());
                simpleLink.setDescription(rl.getDescription());
                simpleLink.setImage(rl.getImage());
                simpleLink.setThumbnail(rl.getThumbnail());

                return simpleLink;
            }
        });
    }

    private Iterable<Topic> res(Iterable<Id> topics, Set<Annotation> annotations) {
        if (Iterables.isEmpty(topics)) { // don't even ask (the resolver)
            return ImmutableList.of();
        }
        return topicResolver.topicsForIds(topics);
    }

    private Iterable<ContentGroup> resolveContentGroups(Iterable<Id> contentGroups, Set<Annotation> annotations) {
        if (Iterables.isEmpty(contentGroups)) { // don't even ask (the resolver)
            return ImmutableList.of();
        }
        return Iterables.transform(contentGroupResolver.findByIds(contentGroups).asResolvedMap().values(), new Function<Identified, ContentGroup>() {

            @Override
            public ContentGroup apply(Identified input) {
                return (ContentGroup) input;
            }
        });
    }

    public Iterable<KeyPhrase> simplifyPhrases(F content) {
        return Iterables.transform(content.getKeyPhrases(), new Function<org.atlasapi.media.entity.KeyPhrase, KeyPhrase>() {

            @Override
            public KeyPhrase apply(org.atlasapi.media.entity.KeyPhrase input) {
                return new KeyPhrase(input.getPhrase(), toPublisherDetails(input.getPublisher()), input.getWeighting());
            }
        });
    }

    private List<org.atlasapi.media.entity.simple.Item> clipToSimple(List<Clip> clips, final Set<Annotation> annotations, final ApplicationConfiguration config) {
        return Lists.transform(clips, new Function<Clip, org.atlasapi.media.entity.simple.Item>() {

            @Override
            public org.atlasapi.media.entity.simple.Item apply(Clip clip) {
                return simplify(clip, annotations, config);
            }
        });
    }

    private List<org.atlasapi.media.entity.simple.TopicRef> topicRefToSimple(final Content content, List<TopicRef> contentTopics, final Set<Annotation> annotations, final ApplicationConfiguration config) {

        final Map<Id, Topic> topics = Maps.uniqueIndex(res(Iterables.transform(contentTopics, TOPICREF_TO_TOPIC_ID), annotations), TOPIC_TO_TO_TOPIC_ID);

        return Lists.transform(contentTopics, new Function<TopicRef, org.atlasapi.media.entity.simple.TopicRef>() {

            @Override
            public org.atlasapi.media.entity.simple.TopicRef apply(TopicRef topicRef) {
                org.atlasapi.media.entity.simple.TopicRef tr = new org.atlasapi.media.entity.simple.TopicRef();
                tr.setSupervised(topicRef.isSupervised());
                tr.setWeighting(topicRef.getWeighting());
                tr.setRelationship(topicRef.getRelationship().toString());
                tr.setOffset(topicRef.getOffset());
                tr.setTopic(topicSimplifier.simplify(topics.get(topicRef.getTopic()), annotations, config));
                if (annotations.contains(Annotation.PUBLISHER)) {
                    tr.setPublisher(toPublisherDetails(topicRef.getPublisher()));
                }
                return tr;
            }
        });
    }

    private Iterable<org.atlasapi.media.entity.simple.ContentGroup> contentGroupRefToSimple(List<ContentGroupRef> refs, final Set<Annotation> annotations, final ApplicationConfiguration config) {

        Iterable<ContentGroup> groups = resolveContentGroups(Iterables.transform(refs, CONTENT_GROUP_REF_TO_CONTENT_GROUP_ID), annotations);

        return Iterables.transform(groups, new Function<ContentGroup, org.atlasapi.media.entity.simple.ContentGroup>() {

            @Override
            public org.atlasapi.media.entity.simple.ContentGroup apply(ContentGroup group) {
                return contentGroupSimplifier.simplify(group, annotations, config);
            }
        });
    }
    private final Function<TopicRef, Id> TOPICREF_TO_TOPIC_ID = new Function<TopicRef, Id>() {

        @Override
        public Id apply(TopicRef input) {
            return input.getTopic();
        }
    };
    private static Function<Topic, Id> TOPIC_TO_TO_TOPIC_ID = new Function<Topic, Id>() {

        @Override
        public Id apply(Topic input) {
            return input.getId();
        }
    };
    private final Function<ContentGroupRef, Id> CONTENT_GROUP_REF_TO_CONTENT_GROUP_ID = new Function<ContentGroupRef, Id>() {

        @Override
        public Id apply(ContentGroupRef input) {
            return input.getId();
        }
    };
    private static Function<ContentGroup, Id> CONTENT_GROUP_TO_CONTENT_GROUP_ID = new Function<ContentGroup, Id>() {

        @Override
        public Id apply(ContentGroup input) {
            return input.getId();
        }
    };

    protected abstract org.atlasapi.media.entity.simple.Item simplify(Item item, Set<Annotation> annotations, ApplicationConfiguration config);
}
