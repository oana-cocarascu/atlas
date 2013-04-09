package org.atlasapi.remotesite.voila;

import static org.atlasapi.feeds.utils.UpdateProgress.FAILURE;
import static org.atlasapi.feeds.utils.UpdateProgress.SUCCESS;
import static org.atlasapi.persistence.logging.AdapterLogEntry.warnEntry;

import java.util.List;

import javax.annotation.Nullable;

import org.atlasapi.feeds.utils.UpdateProgress;
import org.atlasapi.media.content.Container;
import org.atlasapi.media.content.Content;
import org.atlasapi.media.entity.Identified;
import org.atlasapi.media.entity.Item;
import org.atlasapi.media.entity.Publisher;
import org.atlasapi.media.entity.TopicRef;
import org.atlasapi.media.topic.Topic;
import org.atlasapi.persistence.content.ContentResolver;
import org.atlasapi.persistence.content.ContentWriter;
import org.atlasapi.persistence.content.ResolvedContent;
import org.atlasapi.persistence.logging.AdapterLog;
import org.atlasapi.persistence.logging.AdapterLogEntry;
import org.atlasapi.persistence.topic.TopicQueryResolver;
import org.atlasapi.persistence.topic.TopicStore;
import org.atlasapi.remotesite.voila.ContentWords.ContentWordsList;
import org.atlasapi.remotesite.voila.ContentWords.WordWeighting;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.ImmutableSet.Builder;
import com.google.common.collect.Iterables;
import com.metabroadcast.common.base.Maybe;

public class ContentTwitterTopicsUpdater {

    private static final String TWITTER_NS = "twitter";
    private final ContentResolver contentResolver;
    private final TopicStore topicStore;
    private final TopicQueryResolver topicResolver;
    private final ContentWriter contentWriter;
    private final CannonTwitterTopicsClient cannonTopicsClient;
    private final AdapterLog log;

    public ContentTwitterTopicsUpdater(CannonTwitterTopicsClient cannonTopicsClient, ContentResolver contentResolver, TopicStore topicStore, TopicQueryResolver topicResolver, ContentWriter contentWriter, AdapterLog log) {
        this.cannonTopicsClient = cannonTopicsClient;
        this.contentResolver = contentResolver;
        this.topicStore = topicStore;
        this.topicResolver = topicResolver;
        this.contentWriter = contentWriter;
        this.log = log;
    }

    public UpdateProgress updateTopics(List<String> contentIds) {

        Optional<ContentWordsList> possibleContentWords = cannonTopicsClient.getContentWordsForIds(contentIds);

        if (!possibleContentWords.isPresent()) {
            return new UpdateProgress(0, contentIds.size());
        }

        ContentWordsList contentWords = possibleContentWords.get();
        
        ImmutableMap<String, ContentWords> contentWordIndex = Maps.uniqueIndex(contentWords.getResults(), new Function<ContentWords, String>(){

            @Override
            public String apply(ContentWords input) {
                return input.getUri();
            }});

        ResolvedContent resolvedContent = contentResolver.findByCanonicalUris(urisForWords(contentWords));

        UpdateProgress result = UpdateProgress.START;
        for (Content content: Iterables.filter(resolvedContent.getAllResolvedResults(), Content.class)) {
            ContentWords contentWordSet = contentWordIndex.get(content.getCanonicalUri());
            try {
                content.setTopicRefs(getTopicRefsFor(contentWordSet).addAll(filter(content.getTopicRefs())).build());
                write(content);
                result = result.reduce(SUCCESS);
            } catch (Exception e) {
                log.record(AdapterLogEntry.errorEntry().withCause(e).withSource(getClass()).withDescription("Failed to update topics for %s", contentWordSet.getUri()));
                result = result.reduce(FAILURE);
            }
        }

        return result;
    }

    private Iterable<? extends TopicRef> filter(List<TopicRef> topicRefs) {
        return Iterables.filter(topicRefs, new Predicate<TopicRef>() {

            @Override
            public boolean apply(TopicRef input) {
                Maybe<Topic> possibleTopic = topicResolver.topicForId(input.getTopic());
                if (possibleTopic.hasValue()) {
                    Topic topic = possibleTopic.requireValue();
                    return !(TWITTER_NS.equals(topic.getNamespace()) && Publisher.METABROADCAST.equals(topic.getPublisher()));
                }
                return false;
            }
        });
    }

    public void write(Content content) {
        if (content instanceof Container) {
            contentWriter.createOrUpdate((Container) content);
        } else {
            contentWriter.createOrUpdate((Item) content);
        }
    }

    public Builder<TopicRef> getTopicRefsFor(ContentWords contentWordSet) {
        Builder<TopicRef> topicRefs = ImmutableSet.builder();
        for (WordWeighting wordWeighting : ImmutableSet.copyOf(contentWordSet.getWords())) {
            Topic topic = topicStore.topicFor(TWITTER_NS, wordWeighting.getUrl()).valueOrNull();
            if (topic == null) {
                throw new IllegalStateException("This should never happen, as topic is either found or created by the topic store, so failing fast.");
            } else {
                topic.setTitle(wordWeighting.getContent());
                topic.setPublisher(Publisher.METABROADCAST);
                topic.setType(Topic.Type.SUBJECT);
                topicStore.write(topic);
                topicRefs.add(new TopicRef(topic, wordWeighting.getWeight() / 100.0f, false, TopicRef.Relationship.ABOUT));
            }
        }
        return topicRefs;
    }

    public Iterable<String> urisForWords(ContentWordsList contentWords) {
        return ImmutableSet.copyOf(Iterables.transform(contentWords.getResults(), new Function<ContentWords, String>() {

            @Override
            public String apply(ContentWords input) {
                return input.getUri();
            }
        }));
    }
}