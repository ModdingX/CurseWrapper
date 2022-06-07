package org.moddingx.cursewrapper.route;

import org.moddingx.cursewrapper.cache.CacheKey;
import org.moddingx.cursewrapper.cache.CurseCache;
import org.moddingx.cursewrapper.route.base.TextRoute;
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
