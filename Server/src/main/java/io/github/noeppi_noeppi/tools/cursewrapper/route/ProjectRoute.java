package io.github.noeppi_noeppi.tools.cursewrapper.route;

import com.google.gson.JsonElement;
import io.github.noeppi_noeppi.tools.cursewrapper.api.CurseWrapperJson;
import io.github.noeppi_noeppi.tools.cursewrapper.api.response.ProjectInfo;
import io.github.noeppi_noeppi.tools.cursewrapper.backend.CurseApi;
import io.github.noeppi_noeppi.tools.cursewrapper.backend.data.response.ModResponse;
import io.github.noeppi_noeppi.tools.cursewrapper.cache.CacheKey;
import io.github.noeppi_noeppi.tools.cursewrapper.cache.CurseCache;
import io.github.noeppi_noeppi.tools.cursewrapper.convert.ApiConverter;
import io.github.noeppi_noeppi.tools.cursewrapper.route.base.JsonRoute;
import spark.Request;
import spark.Response;
import spark.Service;

import java.io.IOException;

public class ProjectRoute extends JsonRoute {

    public ProjectRoute(Service spark, CurseApi api, CurseCache cache) {
        super(spark, api, cache);
    }

    @Override
    protected JsonElement apply(Request request, Response response) throws IOException {
        return CurseWrapperJson.toJson(this.cache.get(CacheKey.PROJECT, integer(request, "projectId"), this::resolve));
    }

    private ProjectInfo resolve(int projectId) throws IOException {
        return ApiConverter.project(this.api.request("mods/" + projectId, ModResponse.class).data);
    }
}
