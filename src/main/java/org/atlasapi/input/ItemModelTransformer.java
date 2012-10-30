package org.atlasapi.input;

import org.atlasapi.media.entity.Episode;
import org.atlasapi.media.entity.Film;
import org.atlasapi.media.entity.Item;
import org.atlasapi.media.entity.ParentRef;
import org.atlasapi.media.entity.Song;
import org.atlasapi.persistence.content.ContentResolver;
import org.atlasapi.persistence.topic.TopicStore;

public class ItemModelTransformer extends ContentModelTransformer<org.atlasapi.media.entity.simple.Item, Item> {

    public ItemModelTransformer(ContentResolver resolver, TopicStore topicStore) {
        super(resolver, topicStore);
    }

    @Override
    protected Item createOutput(org.atlasapi.media.entity.simple.Item inputItem) {
        String type = inputItem.getType();
        Item item;
        if ("episode".equals(type)) {
            item = createEpisode(inputItem);
        } else if ("film".equals(type)) {
            Film film = new Film();
            item = film;
        } else if ("song".equals(type)) {
            Song song = new Song();
            item = song;
        } else {
            item = new Item();
        }
        return setItemFields(item, inputItem);
    }

    protected Item setItemFields(Item item, org.atlasapi.media.entity.simple.Item inputItem) {
        return item;
    }

    protected Item createEpisode(org.atlasapi.media.entity.simple.Item inputItem) {
        Episode episode = new Episode();
        episode.setSeriesNumber(inputItem.getSeriesNumber());
        episode.setEpisodeNumber(inputItem.getEpisodeNumber());
        if (inputItem.getSeriesSummary() != null) {
            episode.setSeriesRef(new ParentRef(inputItem.getSeriesSummary().getUri()));
        }
        return episode;
    }

}
