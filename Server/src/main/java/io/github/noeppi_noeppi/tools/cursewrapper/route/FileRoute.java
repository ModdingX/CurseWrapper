package io.github.noeppi_noeppi.tools.cursewrapper.route;

import com.google.gson.JsonElement;
import io.github.noeppi_noeppi.tools.cursewrapper.api.CurseWrapperJson;
import io.github.noeppi_noeppi.tools.cursewrapper.api.response.FileInfo;
import io.github.noeppi_noeppi.tools.cursewrapper.backend.CurseApi;
import io.github.noeppi_noeppi.tools.cursewrapper.backend.data.response.ModFileResponse;
import io.github.noeppi_noeppi.tools.cursewrapper.cache.CacheKey;
import io.github.noeppi_noeppi.tools.cursewrapper.cache.CurseCache;
import io.github.noeppi_noeppi.tools.cursewrapper.convert.ApiConverter;
import io.github.noeppi_noeppi.tools.cursewrapper.route.base.JsonRoute;
import spark.Request;
import spark.Response;
import spark.Service;

import java.io.IOException;

public class FileRoute extends JsonRoute {

    public FileRoute(Service spark, CurseApi api, CurseCache cache) {
        super(spark, api, cache);
    }

    @Override
    protected JsonElement apply(Request request, Response response) throws IOException {
        return CurseWrapperJson.toJson(this.cache.get(CacheKey.FILE, new CacheKey.FileKey(integer(request, "projectId"), integer(request, "fileId")), this::resolve));
    }

    private FileInfo resolve(CacheKey.FileKey key) throws IOException {
        return ApiConverter.file(this.api.request("mods/" + key.projectId() + "/files/" + key.fileId(), ModFileResponse.class).data);
    }
}
