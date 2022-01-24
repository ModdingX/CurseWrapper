package io.github.noeppi_noeppi.tools.cursewrapper.cache;

import java.io.IOException;
import java.util.function.Function;

@FunctionalInterface
public interface CacheFunction<T, R> {
    
    R apply(T t) throws IOException;

    static <T> Function<T, T> identity() {
        return t -> t;
    }
}
