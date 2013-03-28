package org.atlasapi.query.v4.topic;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;

import org.atlasapi.media.topic.Topic;
import org.atlasapi.output.AnnotationRegistry;
import org.atlasapi.output.OutputContext;
import org.atlasapi.output.QueryResultWriter;
import org.atlasapi.output.ResponseWriter;
import org.atlasapi.query.common.QueryContext;
import org.atlasapi.query.common.QueryResult;

import com.google.common.collect.FluentIterable;

public class TopicQueryResultWriter implements QueryResultWriter<Topic> {

    private final AnnotationRegistry registry;
    
    public TopicQueryResultWriter(AnnotationRegistry annotations) {
        this.registry = checkNotNull(annotations);
    }

    @Override
    public void write(QueryResult<Topic> result, ResponseWriter writer) throws IOException {
        writer.startResponse();
        writeResult(result, writer);
        writer.finishResponse();
    }

    private void writeResult(QueryResult<Topic> result, ResponseWriter writer)
        throws IOException {

        OutputContext ctxt = outputContext(result.getContext());

        if (result.isListResult()) {
            FluentIterable<Topic> topics = result.getResources();
            writer.writeList(new TopicListWriter(), topics, ctxt);
        } else {
            writer.writeObject(new TopicListWriter(), result.getOnlyResource(), ctxt);
        }
    }
    
    private OutputContext outputContext(QueryContext queryContext) {
        return new OutputContext(
            registry.activeAnnotations(queryContext.getAnnotations()),
            queryContext.getApplicationConfiguration()
        );
    }
    
}