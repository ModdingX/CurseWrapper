package org.moddingx.cursewrapper.route;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.JsonElement;
import org.moddingx.cursewrapper.api.CurseWrapperJson;
import org.moddingx.cursewrapper.api.response.FileInfo;
import org.moddingx.cursewrapper.api.response.ModLoader;
import org.moddingx.cursewrapper.backend.CurseApi;
import org.moddingx.cursewrapper.backend.data.response.ModFileResponse;
import org.moddingx.cursewrapper.backend.data.response.ModFilesResponse;
import org.moddingx.cursewrapper.cache.CacheKey;
import org.moddingx.cursewrapper.cache.CurseCache;
import org.moddingx.cursewrapper.convert.ApiConverter;
import org.moddingx.cursewrapper.convert.GameVersionProcessor;
import org.moddingx.cursewrapper.route.base.JsonRoute;
import spark.Request;
import spark.Response;
import spark.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class LatestFileRoute extends JsonRoute {

    public LatestFileRoute(Service spark, CurseCache cache) {
        super(spark, cache);
    }

    @Override
    protected JsonElement apply(Request request, Response response, RouteData route) throws IOException {
        Set<ModLoader> loaders = Set.copyOf(Arrays.stream(Objects.requireNonNullElse(request.queryParamsValues("loader"), new String[0])).map(ModLoader::get).toList());
        Optional<String> version = Optional.ofNullable(request.queryParams("version"));
        CacheKey.FilesKey key = new CacheKey.FilesKey(this.integer(request, "projectId"), loaders, version);

        return this.cache.runLocked(CacheKey.FILE, () -> {
            Optional<CacheKey.FileKey> fileId = this.cache.get(CacheKey.LATEST_FILE, key, this::resolve);
            if (fileId.isPresent()) {
                FileInfo file = this.cache.get(CacheKey.FILE, fileId.get(), CommonCacheResolvers::file);
                return CurseWrapperJson.toJson(file);
            } else {
                throw this.spark.halt(204);
            }
        });
    }

    private Optional<CacheKey.FileKey> resolve(CurseApi api, CacheKey.FilesKey key) throws IOException {
        int currentIdx = 0;
        int max = 1;
        int counter = 0;
        while (currentIdx < max && counter < 31) {
            Multimap<String, String> params = ArrayListMultimap.create();
            params.put("index", Integer.toString(currentIdx));
            // Try 10 the first time, if there is no match, request more at once
            // to save calls to the API
            params.put("pageSize", Integer.toString(currentIdx == 0 ? 10 : 100));
            ModFilesResponse resp = api.request("mods/" + key.projectId() + "/files", params, ModFilesResponse.class);
            
            currentIdx = resp.pagination.index + resp.pagination.resultCount;
            max = resp.pagination.totalCount;
            counter += 1;
            
            for (ModFileResponse.ModFile file : resp.data) {
                if (GameVersionProcessor.check(file.gameVersions, key.loaders(), key.version().orElse(null))) {
                    CacheKey.FileKey fileKey = new CacheKey.FileKey(file.modId, file.id);
                    this.cache.store(CacheKey.FILE, fileKey, ApiConverter.file(file));
                    return Optional.of(fileKey);
                }
            }
        }

        return Optional.empty();
    }
}
