package org.moddingx.cursewrapper.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import org.moddingx.cursewrapper.api.request.FileFilter;
import org.moddingx.cursewrapper.api.response.FileInfo;
import org.moddingx.cursewrapper.api.response.ProjectInfo;

import javax.annotation.Nullable;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Function;

public class CurseWrapper {
    
    private static final Gson GSON;
    
    static {
        GsonBuilder builder = new GsonBuilder();
        builder.disableHtmlEscaping();
        GSON = builder.create();
    }
    
    private final HttpClient client;
    private final URI baseUri;

    public CurseWrapper(URI baseUri) {
        this.client = HttpClient.newHttpClient();
        this.baseUri = baseUri;
    }

    public String remoteVersion() throws IOException {
        return this.makeStringRequest("version");
    }
    
    public String getSlug(int projectId) throws IOException {
        return this.makeStringRequest("slug/" + projectId);
    }
    
    public ProjectInfo getProject(int projectId) throws IOException {
        return this.makeRequest("project/" + projectId, CurseWrapperJson::projectInfo);
    }
    
    public FileInfo getFile(int projectId, int fileId) throws IOException {
        return this.makeRequest("project/" + projectId + "/file/" + fileId, CurseWrapperJson::fileInfo);
    }

    public String getChangelog(int projectId, int fileId) throws IOException {
        return this.makeStringRequest("project/" + projectId + "/changelog/" + fileId);
    }
    
    public List<ProjectInfo> searchMods(String query) throws IOException {
        return this.searchMods(query, FileFilter.empty());
    }

    public List<ProjectInfo> searchMods(String query, FileFilter filter) throws IOException {
        return this.makeRequest("search", Map.of(
                "query", query,
                "loader", filter.loader().map(l -> l.id).orElse(""),
                "version", filter.gameVersion().orElse("")
        ), json -> CurseWrapperJson.list(json, CurseWrapperJson::projectInfo));
    }

    @Nullable
    public FileInfo getLatestFile(int projectId) throws IOException {
        return getLatestFile(projectId, FileFilter.empty());
    }

    @Nullable
    public FileInfo getLatestFile(int projectId, FileFilter filter) throws IOException {
        try {
            return this.makeRequest("project/" + projectId + "/latest", Map.of(
                    "loader", filter.loader().map(l -> l.id).orElse(""),
                    "version", filter.gameVersion().orElse("")
            ), CurseWrapperJson::fileInfo);
        } catch (RequestException e) {
            if (e.httpStatusCode == 204) {
                return null;
            } else {
                throw e;
            }
        }
    }
    
    public List<FileInfo> getFiles(int projectId) throws IOException {
        return this.getFiles(projectId, FileFilter.empty());
    }
    
    public List<FileInfo> getFiles(int projectId, FileFilter filter) throws IOException {
        return this.makeRequest("project/" + projectId + "/files", Map.of(
                "loader", filter.loader().map(l -> l.id).orElse(""),
                "version", filter.gameVersion().orElse("")
        ), json -> CurseWrapperJson.list(json, CurseWrapperJson::fileInfo));
    }
    
    private <T> T makeRequest(String endpoint, Function<JsonElement, T> mapper) throws IOException {
        return this.makeRequest(endpoint, Map.of(), mapper);
    }
    
    private <T> T makeRequest(String endpoint, Map<String, String> queryArgs, Function<JsonElement, T> mapper) throws IOException {
        String data = this.makeRequest(endpoint, "application/json", queryArgs);
        try {
            return mapper.apply(GSON.fromJson(data, JsonElement.class));
        } catch (JsonParseException | ClassCastException | NoSuchElementException | IllegalStateException e) {
            throw new IOException("Failed to parse result of CurseWrapper server", e);
        }
    }
    
    private String makeStringRequest(String endpoint) throws IOException {
        return this.makeStringRequest(endpoint, Map.of());
    }
    
    private String makeStringRequest(String endpoint, Map<String, String> queryArgs) throws IOException {
        return this.makeRequest(endpoint, "text/plain", queryArgs);
    }
    
    private String makeRequest(String endpoint, String accept, Map<String, String> queryArgs) throws IOException {
        HttpRequest request = HttpRequest.newBuilder().GET()
                .uri(this.getUri(endpoint, queryArgs))
                .header("Accept", accept)
                .header("User-Agent", "Java" + System.getProperty("java.version") + "/CurseWrapper")
                .build();
        return RequestException.send(client, request, HttpResponse.BodySubscribers.ofString(StandardCharsets.UTF_8)).body().strip();
    }
    
    private URI getUri(String endpoint, Map<String, String> queryArgs) {
        String base = this.baseUri.toString();
        StringBuilder sb = new StringBuilder(base);
        if (!base.endsWith("/")) sb.append("/");
        sb.append(endpoint);
        boolean first = true;
        for (Map.Entry<String, String> entry : queryArgs.entrySet()) {
            if (entry.getKey().isEmpty() || entry.getValue().isEmpty()) continue;
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
        return URI.create(sb.toString());
    }
}
