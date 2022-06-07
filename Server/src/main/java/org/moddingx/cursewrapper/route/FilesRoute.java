package org.moddingx.cursewrapper.route;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import org.moddingx.cursewrapper.api.CurseWrapperJson;
import org.moddingx.cursewrapper.api.response.FileInfo;
import org.moddingx.cursewrapper.api.response.ModLoader;
import org.moddingx.cursewrapper.backend.CurseApi;
import org.moddingx.cursewrapper.backend.data.response.ModFileResponse;
import org.moddingx.cursewrapper.backend.data.response.ModFilesResponse;
import org.moddingx.cursewrapper.cache.CacheKey;
import org.moddingx.cursewrapper.cache.CacheUtils;
import org.moddingx.cursewrapper.cache.CurseCache;
import org.moddingx.cursewrapper.convert.ApiConverter;
import org.moddingx.cursewrapper.convert.GameVersionProcessor;
import org.moddingx.cursewrapper.route.base.JsonRoute;
import spark.Request;
import spark.Response;
import spark.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FilesRoute extends JsonRoute {

    public FilesRoute(Service spark, CurseCache cache) {
        super(spark, cache);
    }

    @Override
    protected JsonElement apply(Request request, Response response, RouteData route) throws IOException {
        Optional<ModLoader> loader = Optional.ofNullable(request.queryParams("loader")).map(ModLoader::get);
        Optional<String> version = Optional.ofNullable(request.queryParams("version"));
        CacheKey.FilesKey key = new CacheKey.FilesKey(this.integer(request, "projectId"), loader, version);

        return this.cache.runLocked(CacheKey.FILE, () -> {
            List<CacheKey.FileKey> fileIds = this.cache.get(CacheKey.FILES, key, this::resolve);
            List<FileInfo> files = CacheUtils.bulkMap(this.cache, CacheKey.FILE, fileIds, CommonCacheResolvers::file, CommonCacheResolvers::files);
            JsonArray json = new JsonArray();
            for (FileInfo file : files) {
                json.add(CurseWrapperJson.toJson(file));
            }

            return json;
        });
    }

    private List<CacheKey.FileKey> resolve(CurseApi api, CacheKey.FilesKey key) throws IOException {
        List<ModFileResponse.ModFile> files = new ArrayList<>();
        int currentIdx = 0;
        int max = 1;
        int counter = 0;
        // Stuff relies on this reporting all files, so we need
        // to loop through the pagination until we have them all
        // Still add a counter to prevent endless loops in case
        // something breaks.
        while (currentIdx < max && counter < 30) {
            Multimap<String, String> params = ArrayListMultimap.create();
            params.put("index", Integer.toString(currentIdx));
            params.put("pageSize", Integer.toString(300));
            ModFilesResponse resp = api.request("mods/" + key.projectId() + "/files", params, ModFilesResponse.class);
            
            currentIdx = resp.pagination.index + resp.pagination.resultCount;
            max = resp.pagination.totalCount;
            counter += 1;
            
            files.addAll(resp.data);
        }

        List<CacheKey.FileKey> ids = new ArrayList<>();
        for (ModFileResponse.ModFile file : files) {
            if (GameVersionProcessor.check(file.gameVersions, key.loader().orElse(null), key.version().orElse(null))) {
                CacheKey.FileKey fileKey = new CacheKey.FileKey(file.modId, file.id);
                ids.add(fileKey);
                this.cache.store(CacheKey.FILE, fileKey, ApiConverter.file(file));
            }
        }
        
        return List.copyOf(ids);
    }
}
