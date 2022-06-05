package io.github.noeppi_noeppi.tools.cursewrapper.route;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import io.github.noeppi_noeppi.tools.cursewrapper.api.CurseWrapperJson;
import io.github.noeppi_noeppi.tools.cursewrapper.api.response.ModLoader;
import io.github.noeppi_noeppi.tools.cursewrapper.api.response.ProjectInfo;
import io.github.noeppi_noeppi.tools.cursewrapper.backend.CurseApi;
import io.github.noeppi_noeppi.tools.cursewrapper.backend.data.response.ModResponse;
import io.github.noeppi_noeppi.tools.cursewrapper.backend.data.response.ModSearchResponse;
import io.github.noeppi_noeppi.tools.cursewrapper.backend.data.structure.ModLoaderType;
import io.github.noeppi_noeppi.tools.cursewrapper.backend.data.structure.ModSearchSortField;
import io.github.noeppi_noeppi.tools.cursewrapper.cache.CacheKey;
import io.github.noeppi_noeppi.tools.cursewrapper.cache.CacheUtils;
import io.github.noeppi_noeppi.tools.cursewrapper.cache.CurseCache;
import io.github.noeppi_noeppi.tools.cursewrapper.convert.ApiConverter;
import io.github.noeppi_noeppi.tools.cursewrapper.convert.GameVersionProcessor;
import io.github.noeppi_noeppi.tools.cursewrapper.route.base.JsonRoute;
import spark.Request;
import spark.Response;
import spark.Service;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.*;

public class SearchRoute extends JsonRoute {
    
    public SearchRoute(Service spark, CurseCache cache) {
        super(spark, cache);
    }

    @Override
    protected JsonElement apply(Request request, Response response, RouteData route) throws IOException {
        String query = request.queryParams("query");
        if (query == null) query = "";

        Optional<ModLoader> loader = Optional.ofNullable(request.queryParams("loader")).map(ModLoader::get);
        Optional<String> version = Optional.ofNullable(request.queryParams("version"));
        CacheKey.SearchKey key = new CacheKey.SearchKey(query, loader, version);
        
        return this.cache.runLocked(CacheKey.PROJECT, () -> {
            List<Integer> projectIds = this.cache.get(CacheKey.SEARCH, key, this::resolve);
            List<ProjectInfo> projects = CacheUtils.bulkMap(this.cache, CacheKey.PROJECT, projectIds, CommonCacheResolvers::project, CommonCacheResolvers::projects);

            JsonArray json = new JsonArray();
            for (ProjectInfo project : projects) {
                json.add(CurseWrapperJson.toJson(project));
            }

            return json;
        });
    }
    
    private List<Integer> resolve(CurseApi api, CacheKey.SearchKey key) throws IOException {
        int index = 0;
        List<Integer> projectIds = new ArrayList<>();
        while (projectIds.size() < 30) {
            ResolveData data = this.resolvePartial(api, key, index, Math.max(30 - projectIds.size(), 0));
            projectIds.addAll(data.resolvedProjects());
            index = data.index();
            if (!data.canContinue()) break;
        }
        return projectIds;
    }
    
    private ResolveData resolvePartial(CurseApi api, CacheKey.SearchKey key, int current, int left) throws IOException {
        Set<ModLoaderType> loaders = key.loader().isPresent() ? GameVersionProcessor.forLoader(key.loader().get()) : Set.of();
        // The modLoaderType parameter only works if gameVersion is present and using a loader as game version does not work.
        // Also if reversing the loader yields multiple loader types, it needs to be manually filtered
        boolean needsManualLoaderFiltering = loaders.size() > 1 || (loaders.size() == 1 && key.version().isEmpty());
        
        Multimap<String, String> params = ArrayListMultimap.create();
        params.put("gameId", Integer.toString(CurseApi.MINECRAFT_GAME));
        params.put("classId", Integer.toString(CurseApi.MODS_CLASS));
        params.put("index", Integer.toString(current));
        params.put("pageSize", Integer.toString(Math.round(left * 1.6f)));
        params.put("searchFilter", key.query());
        params.put("sortField", Integer.toString(ModSearchSortField.POPULARITY.ordinal()));
        params.put("sortOrder", "desc");
        if (key.version().isPresent()) {
            params.put("gameVersion", key.version().get());
        }
        if (loaders.size() == 1 && !needsManualLoaderFiltering) {
            params.put("modLoaderType", Integer.toString(loaders.iterator().next().ordinal()));
        }
        ModSearchResponse resp = api.request("mods/search", params, ModSearchResponse.class);
        List<Integer> ids = new ArrayList<>();
        for (ModResponse.Mod mod : resp.data) {
            if (ids.size() >= left) break;
            if (!needsManualLoaderFiltering || this.checkLoader(mod, key.version().orElse(null), loaders)) {
                ids.add(mod.id);
                this.cache.store(CacheKey.PROJECT, mod.id, ApiConverter.project(mod));
            }
        }
        return new ResolveData(
                current + Math.max(0, resp.pagination.resultCount),
                !resp.data.isEmpty() && resp.pagination.totalCount < resp.pagination.index + resp.pagination.resultCount,
                List.copyOf(ids)
        );
    }
    
    private boolean checkLoader(ModResponse.Mod mod, @Nullable String gameVersion, Set<ModLoaderType> loaders) {
        System.err.println(mod.name + " " + gameVersion + " " + loaders);
        // Check the latest files for a file matching the loader.
        
        //noinspection RedundantIfStatement
        if (mod.latestFilesIndexes.stream()
                .filter(file -> gameVersion == null || gameVersion.equals(file.gameVersion))
                .anyMatch(file -> loaders.contains(file.modLoader))
        ) return true;
        
        // We have no efficient way to solve the problem now, as we can't be sure about the loader.
        // Also, the API does not allow filtering by loader when retrieving mod files.
        // And accessing all files for the mod just to filter the loader would take a while
        // So we just assume, the mod is not available
        // Hopefully this works.
        
        return false;
    }
    
    private record ResolveData(int index, boolean canContinue, List<Integer> resolvedProjects) {}
}
