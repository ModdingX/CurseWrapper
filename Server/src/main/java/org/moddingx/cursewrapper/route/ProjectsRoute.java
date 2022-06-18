package org.moddingx.cursewrapper.route;

import com.google.common.collect.Streams;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.moddingx.cursewrapper.api.CurseWrapperJson;
import org.moddingx.cursewrapper.api.response.ProjectInfo;
import org.moddingx.cursewrapper.cache.CacheKey;
import org.moddingx.cursewrapper.cache.CacheUtils;
import org.moddingx.cursewrapper.cache.CurseCache;
import org.moddingx.cursewrapper.route.base.JsonRoute;
import spark.Request;
import spark.Response;
import spark.Service;

import java.io.IOException;
import java.util.List;

public class ProjectsRoute extends JsonRoute {
    
    public ProjectsRoute(Service spark, CurseCache cache) {
        super(spark, cache);
    }

    @Override
    protected JsonElement apply(Request request, Response response, RouteData route) throws IOException {
        List<Integer> projectIds = this.body(request, json -> Streams.stream(json.getAsJsonArray()).map(JsonElement::getAsInt).unordered().distinct().toList());
        return this.cache.runLocked(CacheKey.PROJECT, () -> {
            List<ProjectInfo> projects = CacheUtils.bulkMap(this.cache, CacheKey.PROJECT, projectIds, CommonCacheResolvers::project, CommonCacheResolvers::projects);
            JsonObject json = new JsonObject();
            for (ProjectInfo project : projects) {
                json.add(Integer.toString(project.projectId()), CurseWrapperJson.toJson(project));
            }
            return json;
        });
    }
}
