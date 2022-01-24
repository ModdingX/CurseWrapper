package io.github.noeppi_noeppi.tools.cursewrapper.route;

import io.github.noeppi_noeppi.tools.cursewrapper.backend.CurseApi;
import io.github.noeppi_noeppi.tools.cursewrapper.backend.data.response.ModResponse;
import io.github.noeppi_noeppi.tools.cursewrapper.cache.CacheKey;
import io.github.noeppi_noeppi.tools.cursewrapper.cache.CurseCache;
import io.github.noeppi_noeppi.tools.cursewrapper.route.base.TextRoute;
import spark.Request;
import spark.Response;
import spark.Service;

import java.io.IOException;

public class SlugRoute extends TextRoute {

    public SlugRoute(Service spark, CurseApi api, CurseCache cache) {
        super(spark, api, cache);
    }

    @Override
    protected String apply(Request request, Response response) throws IOException {
        return this.cache.get(CacheKey.SLUG, integer(request, "projectId"), this::resolve);
    }
    
    private String resolve(int projectId) throws IOException {
        return this.api.request("mods/" + projectId, ModResponse.class).data.slug;
    }
}
