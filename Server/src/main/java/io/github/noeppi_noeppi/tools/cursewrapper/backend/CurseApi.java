package io.github.noeppi_noeppi.tools.cursewrapper.backend;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import io.github.noeppi_noeppi.tools.cursewrapper.backend.data.EmptyData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class CurseApi {

    private static final Logger logger = LoggerFactory.getLogger(CurseApi.class);

    private static final Gson GSON;

    static {
        GsonBuilder builder = new GsonBuilder();
        builder.disableHtmlEscaping();
        builder.setDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
        builder.excludeFieldsWithoutExposeAnnotation();
        GSON = builder.create();
    }

    private final String token;
    private final HttpClient client;

    public CurseApi(String token) {
        this.token = token;
        this.client = HttpClient.newHttpClient();
    }

    public void testToken() {
        logger.info("Checking CurseForge token.");
        try {
            request("games", EmptyData.class);
        } catch (IOException e) {
            throw new IllegalStateException("Invalid CurseForge token", e);
        }
    }

    public <O extends CurseData> O request(String endpoint, Class<O> resultCls) throws IOException {
        return request(endpoint, Map.of(), resultCls);
    }
    
    public <O extends CurseData> O request(String endpoint, String key, String value, Class<O> resultCls) throws IOException {
        return request(endpoint, Map.of(key, value), resultCls);
    }
    
    public <O extends CurseData> O request(String endpoint, Map<String, String> args, Class<O> resultCls) throws IOException {
        try {
            String argsPart = "";
            boolean first = true;
            if (!args.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                for (Map.Entry<String, String> entry : args.entrySet()) {
                    if (first) {
                        sb.append("?");
                        first = false;
                    } else {
                        sb.append("&");
                    }
                    sb.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8));
                    sb.append("=");
                    sb.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8));
                }
                argsPart = sb.toString();
            }
            HttpRequest httpRequest = HttpRequest.newBuilder().GET()
                    .uri(URI.create("https://api.curseforge.com/v1/" + endpoint + argsPart))
                    .header("Accept", "application/json")
                    .header("x-api-key", this.token)
                    .build();
            String body = this.client.send(httpRequest, info -> HttpResponse.BodySubscribers.ofString(StandardCharsets.UTF_8)).body();
            try {
                return GSON.fromJson(body, resultCls);
            } catch (JsonParseException e) {
                throw new IOException("Failed to parse data from Curse API", e);
            }
        } catch (InterruptedException e) {
            throw new IOException("Interrupt", e);
        }
    }

    public <I extends CurseData, O extends CurseData> O request(String endpoint, I request, Class<O> resultCls) throws IOException {
        try {
            String json = GSON.toJson(request, request.getClass());
            HttpRequest httpRequest = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(json, StandardCharsets.UTF_8))
                    .uri(URI.create("https://api.curseforge.com/v1/" + endpoint))
                    .header("Accept", "application/json")
                    .header("x-api-key", this.token)
                    .build();
            String body = this.client.send(httpRequest, info -> HttpResponse.BodySubscribers.ofString(StandardCharsets.UTF_8)).body();
            try {
                return GSON.fromJson(body, resultCls);
            } catch (JsonParseException e) {
                throw new IOException("Failed to parse data from Curse API", e);
            }
        } catch (InterruptedException e) {
            throw new IOException("Interrupt", e);
        }
    }
}
