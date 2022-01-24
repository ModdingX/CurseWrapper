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
import io.github.noeppi_noeppi.tools.cursewrapper.cache.CurseCache;
import io.github.noeppi_noeppi.tools.cursewrapper.convert.ApiConverter;
import io.github.noeppi_noeppi.tools.cursewrapper.route.base.JsonRoute;
import spark.Request;
import spark.Response;
import spark.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SearchRoute extends JsonRoute {
    
    public SearchRoute(Service spark, CurseApi api, CurseCache cache) {
        super(spark, api, cache);
    }

    @Override
    protected JsonElement apply(Request request, Response response) throws IOException {
        String query = request.queryParams("query");
        if (query == null) query = "";

        Optional<ModLoader> loader = Optional.ofNullable(request.queryParams("loader")).map(ModLoader::get);
        Optional<String> version = Optional.ofNullable(request.queryParams("version"));
        CacheKey.SearchKey key = new CacheKey.SearchKey(query, loader, version);
        
        List<Integer> projectIds = this.cache.get(CacheKey.SEARCH, key, this::resolve);

        JsonArray json = new JsonArray();
        for (int projectId : projectIds) {
            json.add(CurseWrapperJson.toJson(this.cache.get(CacheKey.PROJECT, projectId, this::resolveProject)));
        }
        
        return json;
    }
    
    private List<Integer> resolve(CacheKey.SearchKey key) throws IOException {
        Multimap<String, String> params = ArrayListMultimap.create();
        params.put("gameId", Integer.toString(CurseApi.MINECRAFT_GAME));
        params.put("classId", Integer.toString(CurseApi.MODS_CLASS));
        params.put("index", Integer.toString(0));
        params.put("pageSize", Integer.toString(30));
        params.put("searchFilter", key.query());
        params.put("sortField", Integer.toString(ModSearchSortField.POPULARITY.ordinal()));
        params.put("sortOrder", "desc");
        if (key.version().isPresent()) {
            params.put("gameVersion", key.version().get());
        }
        // TODO mod loader filtering, see https://discord.com/channels/900128427150028811/900188519081848865/935219529645195264
        params.put("modLoaderType", "fabric");
        if (key.loader().isPresent()) {
            params.put("modLoaderType", Integer.toString(ModLoaderType.reverse(key.loader().get()).ordinal()));
        }
        ModSearchResponse resp = this.api.request("mods/search", params, ModSearchResponse.class);
        List<Integer> ids = new ArrayList<>();
        for (ModResponse.Mod mod : resp.data) {
            ids.add(mod.id);
            this.cache.store(CacheKey.PROJECT, mod.id, ApiConverter.project(mod));
        }
        return List.copyOf(ids);
    }

    private ProjectInfo resolveProject(int projectId) throws IOException {
        return ApiConverter.project(this.api.request("mods/" + projectId, ModResponse.class).data);
    }
}
