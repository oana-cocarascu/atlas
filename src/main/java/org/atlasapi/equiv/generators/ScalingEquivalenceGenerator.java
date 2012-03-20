package org.atlasapi.equiv.generators;

import org.atlasapi.equiv.results.description.ResultDescription;
import org.atlasapi.equiv.results.scores.ScaledScoredEquivalents;
import org.atlasapi.equiv.results.scores.ScoredEquivalents;
import org.atlasapi.media.content.Content;

import com.google.common.base.Function;

public class ScalingEquivalenceGenerator<T extends Content> implements ContentEquivalenceGenerator<T> {

    public static <T extends Content> ScalingEquivalenceGenerator<T> scale(ContentEquivalenceGenerator<T> delegate, final double scaler) {
        return scale(delegate, new Function<Double, Double>() {
            @Override
            public Double apply(Double input) {
                return input * scaler;
            }
        });
    }
    
    public static <T extends Content> ScalingEquivalenceGenerator<T> scale(ContentEquivalenceGenerator<T> delegate, Function<Double, Double> basicScale) {
      return new ScalingEquivalenceGenerator<T>(delegate, basicScale);
    }
    
    private final ContentEquivalenceGenerator<T> delegate;
    private final Function<Double, Double> scalingFunction;

    public ScalingEquivalenceGenerator(ContentEquivalenceGenerator<T> delegate, Function<Double, Double> scalingFunction) {
        this.delegate = delegate;
        this.scalingFunction = scalingFunction;
    }
    
    @Override
    public ScoredEquivalents<T> generate(T content, ResultDescription desc) {
        return ScaledScoredEquivalents.<T>scale(delegate.generate(content, desc), scalingFunction);
    }

    @Override
    public String toString() {
        return String.format("%s (scaled by %s)", delegate, scalingFunction);
    }
}
