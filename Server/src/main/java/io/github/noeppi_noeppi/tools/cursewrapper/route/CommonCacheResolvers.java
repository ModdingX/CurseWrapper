package io.github.noeppi_noeppi.tools.cursewrapper.route;

import io.github.noeppi_noeppi.tools.cursewrapper.api.response.FileInfo;
import io.github.noeppi_noeppi.tools.cursewrapper.api.response.ProjectInfo;
import io.github.noeppi_noeppi.tools.cursewrapper.backend.CurseApi;
import io.github.noeppi_noeppi.tools.cursewrapper.backend.data.request.BulkFilesRequest;
import io.github.noeppi_noeppi.tools.cursewrapper.backend.data.request.BulkModsRequest;
import io.github.noeppi_noeppi.tools.cursewrapper.backend.data.response.BulkFilesResponse;
import io.github.noeppi_noeppi.tools.cursewrapper.backend.data.response.BulkModsResponse;
import io.github.noeppi_noeppi.tools.cursewrapper.backend.data.response.ModFileResponse;
import io.github.noeppi_noeppi.tools.cursewrapper.backend.data.response.ModResponse;
import io.github.noeppi_noeppi.tools.cursewrapper.cache.CacheKey;
import io.github.noeppi_noeppi.tools.cursewrapper.convert.ApiConverter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CommonCacheResolvers {

    public static String slug(CurseApi api, int projectId) throws IOException {
        return api.request("mods/" + projectId, ModResponse.class).data.slug;
    }

    public static ProjectInfo project(CurseApi api, int projectId) throws IOException {
        return ApiConverter.project(api.request("mods/" + projectId, ModResponse.class).data);
    }

    public static FileInfo file(CurseApi api, CacheKey.FileKey key) throws IOException {
        return ApiConverter.file(api.request("mods/" + key.projectId() + "/files/" + key.fileId(), ModFileResponse.class).data);
    }
    
    public static Map<Integer, ProjectInfo> projects(CurseApi api, Set<Integer> projectIds) throws IOException {
        List<ModResponse.Mod> mods = api.request("mods", new BulkModsRequest(List.copyOf(projectIds)), BulkModsResponse.class).data;
        Map<Integer, ProjectInfo> map = new HashMap<>();
        for (ModResponse.Mod mod : mods) {
            map.put(mod.id, ApiConverter.project(mod));
        }
        return map;
    }

    public static Map<CacheKey.FileKey, FileInfo> files(CurseApi api, Set<CacheKey.FileKey> fileKeys) throws IOException {
        List<Integer> fileIds = fileKeys.stream().map(CacheKey.FileKey::fileId).toList();
        List<ModFileResponse.ModFile> files = api.request("mods/files", new BulkFilesRequest(fileIds), BulkFilesResponse.class).data;
        Map<CacheKey.FileKey, FileInfo> map = new HashMap<>();
        for (ModFileResponse.ModFile file : files) {
            map.put(new CacheKey.FileKey(file.modId, file.id), ApiConverter.file(file));
        }
        return map;
    }
}
