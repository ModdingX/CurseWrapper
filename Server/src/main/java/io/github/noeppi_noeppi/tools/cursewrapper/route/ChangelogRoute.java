package io.github.noeppi_noeppi.tools.cursewrapper.route;

import io.github.noeppi_noeppi.tools.cursewrapper.backend.CurseApi;
import io.github.noeppi_noeppi.tools.cursewrapper.backend.data.response.ModFileChangelogResponse;
import io.github.noeppi_noeppi.tools.cursewrapper.cache.CacheKey;
import io.github.noeppi_noeppi.tools.cursewrapper.cache.CurseCache;
import io.github.noeppi_noeppi.tools.cursewrapper.route.base.TextRoute;
import spark.Request;
import spark.Response;
import spark.Service;

import java.io.IOException;

public class ChangelogRoute extends TextRoute {

    public ChangelogRoute(Service spark, CurseCache cache) {
        super(spark, cache);
    }

    @Override
    protected String apply(Request request, Response response) throws IOException {
        return this.cache.get(CacheKey.CHANGELOG, new CacheKey.FileKey(this.integer(request, "projectId"), this.integer(request, "fileId")), this::resolve);
    }

    private String resolve(CurseApi api, CacheKey.FileKey key) throws IOException {
        return api.request("mods/" + key.projectId() + "/files/" + key.fileId() + "/changelog", ModFileChangelogResponse.class).data;
    }
}
