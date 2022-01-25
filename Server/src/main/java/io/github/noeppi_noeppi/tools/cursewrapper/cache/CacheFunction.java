package io.github.noeppi_noeppi.tools.cursewrapper.cache;

import io.github.noeppi_noeppi.tools.cursewrapper.backend.CurseApi;

import java.io.IOException;
import java.util.function.Function;

@FunctionalInterface
public interface CacheFunction<T, R> {
    
    R apply(CurseApi api, T t) throws IOException;

    static <T> Function<T, T> identity() {
        return t -> t;
    }
}
