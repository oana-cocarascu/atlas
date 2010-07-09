package org.atlasapi.query.content;

import java.util.List;
import java.util.Set;

import org.atlasapi.content.criteria.AttributeQuery;
import org.atlasapi.content.criteria.ContentQuery;
import org.atlasapi.content.criteria.attribute.Attribute;
import org.atlasapi.content.criteria.attribute.Attributes;
import org.atlasapi.media.entity.Brand;
import org.atlasapi.media.entity.Content;
import org.atlasapi.media.entity.Countries;
import org.atlasapi.media.entity.Country;
import org.atlasapi.media.entity.Item;
import org.atlasapi.media.entity.Playlist;
import org.atlasapi.media.entity.Publisher;
import org.atlasapi.persistence.content.query.KnownTypeQueryExecutor;
import org.atlasapi.persistence.content.query.QueryFragmentExtractor;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.metabroadcast.common.base.Maybe;

public class UniqueContentForUriQueryExecutor implements KnownTypeQueryExecutor {

    private final KnownTypeQueryExecutor delegate;

    public UniqueContentForUriQueryExecutor(KnownTypeQueryExecutor delegate) {
        this.delegate = delegate;
    }

    @Override
    public List<Brand> executeBrandQuery(ContentQuery query) {
        List<Brand> results = delegate.executeBrandQuery(query);

        return removeDuplicateElements(results, query, UriExtractor.extractFrom(query));
    }

    @Override
    public List<Item> executeItemQuery(ContentQuery query) {
        List<Item> results = delegate.executeItemQuery(query);

        return removeDuplicateElements(results, query, UriExtractor.extractFrom(query));
    }

    @Override
    public List<Playlist> executePlaylistQuery(ContentQuery query) {
        List<Playlist> results = delegate.executePlaylistQuery(query);

        return removeDuplicateElements(results, query, UriExtractor.extractFrom(query));
    }

    private <T extends Content> List<T> removeDuplicateElements(List<T> results, ContentQuery query, Set<String> uris) {
        List<T> nonDuplicates = Lists.newArrayList();
        if (uris.isEmpty()) {
            nonDuplicates.addAll(results);
        } else {
            for (T result : results) {
                Maybe<T> existing = existing(nonDuplicates, result);
                if (existing.isNothing()) {
                    nonDuplicates.add(result);
                } else {
                    if (shouldSwap(existing.requireValue(), result, query, uris)) {
                        int index = nonDuplicates.indexOf(existing.requireValue());
                        if (index != -1) {
                            nonDuplicates.remove(existing.requireValue());
                            nonDuplicates.add(index, result);
                        }
                    }
                }
            }
        }

        return nonDuplicates;
    }

    @SuppressWarnings("unchecked")
    private boolean shouldSwap(Content existing, Content duplicate, ContentQuery query, Set<String> uris) {
        if (uris.contains(existing.getCanonicalUri())) {
            return false;
        }
        if (uris.contains(duplicate.getCanonicalUri())) {
            return true;
        }

        Maybe<AttributeQuery<?>> withLocation = QueryFragmentExtractor.extract(query, Sets.<Attribute<?>> newHashSet(Attributes.POLICY_AVAILABLE_COUNTRY));
        if (withLocation.hasValue()) {
            Set<Country> countries = Countries.fromCodes((List<String>) withLocation.requireValue().getValue());

            Maybe<Publisher> existingPublisher = Publisher.fromKey(existing.getPublisher());
            Maybe<Publisher> duplicatePublisher = Publisher.fromKey(duplicate.getPublisher());

            if (duplicatePublisher.hasValue() && countries.contains(duplicatePublisher.requireValue().country())
                    && (existingPublisher.isNothing() || !countries.contains(existingPublisher.requireValue().country()))) {
                return true;
            }
        }
        return false;
    }

    private <T extends Content> Maybe<T> existing(List<T> nonDuplicates, T element) {
        for (T nonDuplicate : nonDuplicates) {
            if (!Sets.intersection(element.getAllUris(), nonDuplicate.getAllUris()).isEmpty()) {
                return Maybe.fromPossibleNullValue(nonDuplicate);
            }
        }
        return Maybe.nothing();
    }
}
