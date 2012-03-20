package org.atlasapi.equiv.update;

import java.util.List;
import java.util.Map;

import org.atlasapi.equiv.results.EquivalenceResult;
import org.atlasapi.media.content.Content;
import org.atlasapi.media.content.Publisher;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;

public class PublisherSwitchingContentEquivalenceUpdater implements ContentEquivalenceUpdater<Content> {

    private Map<Publisher, ContentEquivalenceUpdater<Content>> backingMap;

    public PublisherSwitchingContentEquivalenceUpdater(Map<Publisher, ContentEquivalenceUpdater<Content>> publisherUpdaters) {
        this.backingMap = ImmutableMap.copyOf(publisherUpdaters);
    }
    
    public ContentEquivalenceUpdater<Content> updaterFor(Publisher publisher) {
        return backingMap.get(publisher);
    }

    @Override
    public EquivalenceResult<Content> updateEquivalences(Content content, Optional<List<Content>> externalCandidates) {
        return updaterFor(content.getPublisher()).updateEquivalences(content, externalCandidates);
    }
}
