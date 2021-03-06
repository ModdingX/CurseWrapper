package org.moddingx.cursewrapper.route.base;

import org.moddingx.cursewrapper.cache.CurseCache;
import spark.Request;
import spark.Response;
import spark.Service;

import java.io.IOException;
import java.util.function.Function;

public abstract class TextRoute extends CurseRoute<String> {

    protected TextRoute(Service spark, CurseCache cache) {
        super(spark, cache, "text/plain", Function.identity());
    }

    @Override
    protected abstract String apply(Request request, Response response, RouteData route) throws IOException;
}
