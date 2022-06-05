package io.github.noeppi_noeppi.tools.cursewrapper.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import io.github.noeppi_noeppi.tools.cursewrapper.api.response.*;

import java.math.BigInteger;
import java.net.URI;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class CurseWrapperJson {
    
    public static JsonElement toJson(Instant instant) {
        BigInteger value1 = BigInteger.valueOf(instant.getEpochSecond()).multiply(BigInteger.valueOf(1000000000));
        BigInteger value2 = BigInteger.valueOf(instant.getNano());
        return new JsonPrimitive(value1.add(value2));
    }

    public static Instant instant(JsonElement json) {
        BigInteger value = json.getAsBigInteger();
        BigInteger[] values = value.divideAndRemainder(BigInteger.valueOf(1000000000));
        return Instant.ofEpochSecond(values[0].longValue(), values[1].intValue());
    }
    
    public static JsonElement toJson(Dependency dependency) {
        JsonObject json = new JsonObject();
        json.addProperty("type", dependency.type().id);
        json.addProperty("project", dependency.projectId());
        return json;
    }
    
    public static Dependency dependency(JsonElement json) {
        JsonObject obj = json.getAsJsonObject();
        RelationType type = RelationType.get(obj.get("type").getAsString());
        int projectId = obj.get("project").getAsInt();
        return new Dependency(type, projectId);
    }

    public static JsonElement toJson(FileInfo file) {
        JsonObject json = new JsonObject();
        json.addProperty("project", file.projectId());
        json.addProperty("file", file.fileId());
        json.addProperty("name", file.name());
        json.add("loader", array(file.loader(), l -> new JsonPrimitive(l.id)));
        json.add("versions", array(file.gameVersions(), JsonPrimitive::new));
        json.addProperty("release", file.releaseType().id);
        json.add("date", toJson(file.fileDate()));
        json.add("dependencies", array(file.dependencies(), CurseWrapperJson::toJson));
        return json;
    }
    
    public static FileInfo fileInfo(JsonElement json) {
        JsonObject obj = json.getAsJsonObject();
        int projectId = obj.get("project").getAsInt();
        int fileId = obj.get("file").getAsInt();
        String name = obj.get("name").getAsString();
        List<ModLoader> loader = list(obj.get("loader"), j -> ModLoader.get(j.getAsString()));
        List<String> versions = list(obj.get("versions"), JsonElement::getAsString);
        ReleaseType release = ReleaseType.get(obj.get("release").getAsString());
        Instant date = instant(obj.get("date"));
        List<Dependency> dependencies = list(obj.get("dependencies"), CurseWrapperJson::dependency);
        return new FileInfo(projectId, fileId, name, loader, versions, release, date, dependencies);
    }
    
    public static JsonElement toJson(ProjectInfo project) {
        JsonObject json = new JsonObject();
        json.addProperty("project", project.projectId());
        json.addProperty("slug", project.slug());
        json.addProperty("name", project.name());
        json.addProperty("owner", project.owner());
        json.addProperty("summary", project.summary());
        json.addProperty("distribution", project.distribution());
        json.addProperty("website", project.website().toString());
        json.addProperty("thumbnail", project.thumbnail().toString());
        return json;
    }

    public static ProjectInfo projectInfo(JsonElement json) {
        JsonObject obj = json.getAsJsonObject();
        int projectId = obj.get("project").getAsInt();
        String slug = obj.get("slug").getAsString();
        String name = obj.get("name").getAsString();
        String owner = obj.get("owner").getAsString();
        String summary = obj.get("summary").getAsString();
        boolean distribution = obj.get("distribution").getAsBoolean();
        URI website = URI.create(obj.get("website").getAsString());
        URI thumbnail = URI.create(obj.get("thumbnail").getAsString());
        return new ProjectInfo(projectId, slug, name, owner, summary, distribution, website, thumbnail);
    }

    public static <T> JsonArray array(List<T> values, Function<? super T, ? extends JsonElement> mapper) {
        JsonArray array = new JsonArray();
        for (T elem : values) {
            array.add(mapper.apply(elem));
        }
        return array;
    }

    public static <T> List<T> list(JsonElement json, Function<? super JsonElement, ? extends T> mapper) {
        List<T> list = new ArrayList<>();
        for (JsonElement elem : json.getAsJsonArray()) {
            list.add(mapper.apply(elem));
        }
        return List.copyOf(list);
    }
}
