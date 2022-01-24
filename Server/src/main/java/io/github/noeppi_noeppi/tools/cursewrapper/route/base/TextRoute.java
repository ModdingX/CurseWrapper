package io.github.noeppi_noeppi.tools.cursewrapper.route.base;

import io.github.noeppi_noeppi.tools.cursewrapper.backend.CurseApi;
import io.github.noeppi_noeppi.tools.cursewrapper.cache.CurseCache;
import spark.Request;
import spark.Response;
import spark.Service;

import java.io.IOException;
import java.util.function.Function;

public abstract class TextRoute extends CurseRoute<String> {

    protected TextRoute(Service spark, CurseApi api, CurseCache cache) {
        super(spark, api, cache, "text/plain", Function.identity());
    }

    @Override
    protected abstract String apply(Request request, Response response) throws IOException;
}
