package org.moddingx.cursewrapper.route.base;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import org.moddingx.cursewrapper.cache.CurseCache;
import spark.Request;
import spark.Response;
import spark.Service;

import java.io.IOException;
import java.util.function.Function;

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
    
    protected final JsonElement body(Request request) {
        return this.body(request, Function.identity());
    }
    
    protected final <T> T body(Request request, Function<JsonElement, T> mapper) {
        String body = request.body();
        if (body == null || body.isEmpty()) {
            throw this.spark.halt(400, "Missing request content");
        }
        JsonElement data;
        try {
            data = GSON.fromJson(body, JsonElement.class);
        } catch (JsonSyntaxException e) {
            throw this.spark.halt(400, "Invalid json: " + e.getMessage());
        }
        try {
            return mapper.apply(data);
        } catch (Exception e) {
            throw this.spark.halt(400, "Invalid request content.");
        }
    }
}
