package org.moddingx.cursewrapper.route;

import org.moddingx.cursewrapper.backend.CurseApi;
import org.moddingx.cursewrapper.backend.data.response.ModFileChangelogResponse;
import org.moddingx.cursewrapper.cache.CacheKey;
import org.moddingx.cursewrapper.cache.CurseCache;
import org.moddingx.cursewrapper.route.base.TextRoute;
import spark.Request;
import spark.Response;
import spark.Service;

import java.io.IOException;

public class ChangelogRoute extends TextRoute {

    public ChangelogRoute(Service spark, CurseCache cache) {
        super(spark, cache);
    }

    @Override
    protected String apply(Request request, Response response, RouteData route) throws IOException {
        return this.cache.get(CacheKey.CHANGELOG, new CacheKey.FileKey(this.integer(request, "projectId"), this.integer(request, "fileId")), this::resolve);
    }

    private String resolve(CurseApi api, CacheKey.FileKey key) throws IOException {
        return api.request("mods/" + key.projectId() + "/files/" + key.fileId() + "/changelog", ModFileChangelogResponse.class).data;
    }
}
