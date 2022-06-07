package org.moddingx.cursewrapper.route;

import com.google.gson.JsonElement;
import org.moddingx.cursewrapper.api.CurseWrapperJson;
import org.moddingx.cursewrapper.cache.CacheKey;
import org.moddingx.cursewrapper.cache.CurseCache;
import org.moddingx.cursewrapper.route.base.JsonRoute;
import spark.Request;
import spark.Response;
import spark.Service;

import java.io.IOException;

public class ProjectRoute extends JsonRoute {

    public ProjectRoute(Service spark, CurseCache cache) {
        super(spark, cache);
    }

    @Override
    protected JsonElement apply(Request request, Response response, RouteData route) throws IOException {
        return CurseWrapperJson.toJson(this.cache.get(CacheKey.PROJECT, this.integer(request, "projectId"), CommonCacheResolvers::project));
    }
}
