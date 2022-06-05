package io.github.noeppi_noeppi.tools.cursewrapper.route;

import io.github.noeppi_noeppi.tools.cursewrapper.cache.CacheKey;
import io.github.noeppi_noeppi.tools.cursewrapper.cache.CurseCache;
import io.github.noeppi_noeppi.tools.cursewrapper.route.base.TextRoute;
import spark.Request;
import spark.Response;
import spark.Service;

import java.io.IOException;

public class SlugRoute extends TextRoute {

    public SlugRoute(Service spark, CurseCache cache) {
        super(spark, cache);
    }

    @Override
    protected String apply(Request request, Response response, RouteData route) throws IOException {
        route.allowCaching();
        return this.cache.get(CacheKey.SLUG, this.integer(request, "projectId"), CommonCacheResolvers::slug);
    }
}
