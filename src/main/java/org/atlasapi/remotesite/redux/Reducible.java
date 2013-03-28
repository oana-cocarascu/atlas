package org.atlasapi.remotesite.redux;

public interface Reducible<T extends Reducible<T>> {
    
    T reduce(T other);
    
    public static class NullReducible implements Reducible<NullReducible> {

        @Override
        public NullReducible reduce(NullReducible other) {
            return this;
        }
        
    }
}