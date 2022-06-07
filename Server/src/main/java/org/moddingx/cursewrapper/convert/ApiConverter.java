package org.moddingx.cursewrapper.convert;

import org.moddingx.cursewrapper.api.response.Dependency;
import org.moddingx.cursewrapper.api.response.FileInfo;
import org.moddingx.cursewrapper.api.response.ProjectInfo;
import org.moddingx.cursewrapper.backend.data.response.ModFileResponse;
import org.moddingx.cursewrapper.backend.data.response.ModResponse;

import java.net.URI;

public class ApiConverter {

    private static final URI FALLBACK_LOGO = URI.create("https://singlecolorimage.com/get/e4e0e9/512x512.png");
    
    public static ProjectInfo project(ModResponse.Mod mod) {
        return new ProjectInfo(
                mod.id, mod.slug, mod.name, mod.authors.stream().findFirst().map(a -> a.name).orElse("unknown"),
                mod.summary, mod.allowModDistribution == null || mod.allowModDistribution,
                URI.create(mod.links.websiteUrl), mod.logo == null ? FALLBACK_LOGO : URI.create(mod.logo.url)
        );
    }
    
    public static FileInfo file(ModFileResponse.ModFile file) {
        GameVersionProcessor.GameVersionData versionData = GameVersionProcessor.data(file.gameVersions);
        return new FileInfo(
                file.modId, file.id, file.fileName, versionData.loader(), versionData.versions(), file.releaseType.type,
                file.fileDate.toInstant(), file.dependencies.stream().map(ApiConverter::dependency).toList()
        );
    }

    public static Dependency dependency(ModFileResponse.Dependency dependency) {
        return new Dependency(dependency.relationType.type, dependency.modId);
    }
}
