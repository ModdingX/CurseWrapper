package io.github.noeppi_noeppi.tools.cursewrapper.route.base;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import io.github.noeppi_noeppi.tools.cursewrapper.cache.CurseCache;
import spark.Request;
import spark.Response;
import spark.Service;

import java.io.IOException;

public abstract class JsonRoute extends CurseRoute<JsonElement> {

    private static final Gson GSON;

    static {
        GsonBuilder builder = new GsonBuilder();
        builder.disableHtmlEscaping();
        GSON = builder.create();
    }

    protected JsonRoute(Service spark, CurseCache cache) {
        super(spark, cache, "application/json", GSON::toJson);
    }

    @Override
    protected abstract JsonElement apply(Request request, Response response, RouteData route) throws IOException;
}
