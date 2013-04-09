package org.atlasapi.remotesite.youview;

import nu.xom.Element;

import org.atlasapi.media.content.Content;
import org.atlasapi.media.entity.Identified;
import org.atlasapi.media.entity.Item;
import org.atlasapi.persistence.content.ContentResolver;
import org.atlasapi.persistence.content.ContentWriter;
import org.atlasapi.remotesite.ContentMerger;

import com.google.common.collect.ImmutableSet;
import com.metabroadcast.common.base.Maybe;

public class DefaultYouViewXmlElementHandler implements YouViewXmlElementHandler {

    private final YouViewContentExtractor extractor;
    private final ContentResolver resolver;
    private final ContentWriter writer;

    public DefaultYouViewXmlElementHandler(YouViewContentExtractor extractor, ContentResolver resolver, ContentWriter writer) {
        this.extractor = extractor;
        this.resolver = resolver;
        this.writer = writer;
    }
    
    @Override
    public void handle(Element element) {
        Content content = extractor.extract(element);
        Maybe<Identified> existing = resolve(content.getCanonicalUri());
        if (existing.isNothing()) {
            write(content);
        } else {
            Identified identified = existing.requireValue();
            if (content instanceof Item) {
                write(ContentMerger.merge(ContentMerger.asItem(identified), (Item) content));
            }
        }
    }

    private Maybe<Identified> resolve(String uri) {
        ImmutableSet<String> uris = ImmutableSet.of(uri);
        return resolver.findByCanonicalUris(uris).getFirstValue();
    }

    private void write(Content content) {
        writer.createOrUpdate((Item) content);
    }
}
