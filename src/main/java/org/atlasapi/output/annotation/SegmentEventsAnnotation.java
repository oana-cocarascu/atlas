package org.atlasapi.output.annotation;


import java.io.IOException;

import org.atlasapi.media.content.Content;
import org.atlasapi.output.FieldWriter;
import org.atlasapi.output.OutputContext;
import org.atlasapi.persistence.media.segment.SegmentResolver;


public class SegmentEventsAnnotation extends OutputAnnotation<Content> {

    private final SegmentResolver segmentResolver;

    public SegmentEventsAnnotation(SegmentResolver segmentResolver) {
        super(Content.class);
        this.segmentResolver = segmentResolver;
    }

    @Override
    public void write(Content entity, FieldWriter format, OutputContext ctxt) throws IOException {
        // TODO Auto-generated method stub
        
    }

}