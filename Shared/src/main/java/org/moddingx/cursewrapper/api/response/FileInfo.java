package org.moddingx.cursewrapper.api.response;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public record FileInfo(
        int projectId,
        int fileId,
        String name,
        List<ModLoader> loader,
        List<String> gameVersions,
        ReleaseType releaseType,
        FileEnvironment environment,
        Instant fileDate,
        long fileSize,
        long fingerprint,
        List<Dependency> dependencies,
        Map<String, String> hashes
) {}
