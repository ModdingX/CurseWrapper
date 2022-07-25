package org.moddingx.cursewrapper.route;

import com.google.common.collect.Streams;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import org.moddingx.cursewrapper.api.CurseWrapperJson;
import org.moddingx.cursewrapper.api.response.FileInfo;
import org.moddingx.cursewrapper.backend.data.request.FingerprintRequest;
import org.moddingx.cursewrapper.backend.data.response.FingerprintResponse;
import org.moddingx.cursewrapper.cache.CacheKey;
import org.moddingx.cursewrapper.cache.CurseCache;
import org.moddingx.cursewrapper.convert.ApiConverter;
import org.moddingx.cursewrapper.route.base.JsonRoute;
import spark.Request;
import spark.Response;
import spark.Service;

import java.io.IOException;
import java.util.List;

public class FingerprintsRoute extends JsonRoute {

    public FingerprintsRoute(Service spark, CurseCache cache) {
        super(spark, cache);
    }

    @Override
    protected JsonElement apply(Request request, Response response, RouteData route) throws IOException {
        JsonArray body = this.body(request, JsonElement::getAsJsonArray);
        List<Long> fingerprints = Streams.stream(body).map(JsonElement::getAsLong).distinct().toList();
        if (fingerprints.isEmpty()) {
            return new JsonArray();
        } else {
            FingerprintRequest requestData = new FingerprintRequest(fingerprints);
            FingerprintResponse responseData = this.cache.api.request("fingerprints", requestData, FingerprintResponse.class);
            JsonArray json = new JsonArray();
            for (FingerprintResponse.FingerprintMatch match : responseData.data.exactMatches) {
                FileInfo file = ApiConverter.file(match.file);
                this.cache.store(CacheKey.FILE, new CacheKey.FileKey(file.projectId(), file.fileId()), file);
                json.add(CurseWrapperJson.toJson(file));
            }
            return json;
        }
    }
}
