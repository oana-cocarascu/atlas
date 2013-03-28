package org.atlasapi.messaging.workers;

import org.atlasapi.media.content.Content;
import org.atlasapi.media.content.ContentWriter;
import org.atlasapi.messaging.EntityUpdatedMessage;
import org.atlasapi.persistence.content.ContentResolver;
import org.atlasapi.persistence.content.ResolvedContent;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

public class ContentReadWriter extends AbstractWorker {

    private final ContentResolver contentResolver;
    private final ContentWriter contentWriter;

    public ContentReadWriter(ContentResolver contentResolver, ContentWriter contentWriter) {
        this.contentResolver = contentResolver;
        this.contentWriter = contentWriter;
    }

    @Override
    public void process(EntityUpdatedMessage message) {
        ResolvedContent resolved = contentResolver.findByCanonicalUris(ImmutableList.of(message.getEntityId()));
        for (Content content : Iterables.filter(resolved.getAllResolvedResults(), Content.class)) {
            content.setReadHash(null);//force write
            contentWriter.writeContent(content);
        }
    }
    
}