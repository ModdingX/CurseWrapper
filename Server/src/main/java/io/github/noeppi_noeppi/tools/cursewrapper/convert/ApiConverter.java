package io.github.noeppi_noeppi.tools.cursewrapper.convert;

import io.github.noeppi_noeppi.tools.cursewrapper.api.response.Dependency;
import io.github.noeppi_noeppi.tools.cursewrapper.api.response.FileInfo;
import io.github.noeppi_noeppi.tools.cursewrapper.api.response.ProjectInfo;
import io.github.noeppi_noeppi.tools.cursewrapper.backend.data.response.ModFileResponse;
import io.github.noeppi_noeppi.tools.cursewrapper.backend.data.response.ModResponse;

import java.net.URI;

public class ApiConverter {

    public static ProjectInfo project(ModResponse.Mod mod) {
        return new ProjectInfo(
                mod.id, mod.slug, mod.name, mod.authors.stream().findFirst().map(a -> a.name).orElse("unknown"),
                mod.summary, URI.create(mod.links.websiteUrl), URI.create(mod.logo.url)
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
