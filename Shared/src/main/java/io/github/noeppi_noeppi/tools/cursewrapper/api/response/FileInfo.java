package io.github.noeppi_noeppi.tools.cursewrapper.api.response;

import java.time.Instant;
import java.util.List;

public record FileInfo(
        int projectId,
        int fileId,
        String projectSlug,
        String name,
        List<ModLoader> loader,
        List<String> gameVersions,
        ReleaseType releaseType,
        Instant fileDate,
        List<Dependency> dependencies
) implements Comparable<FileInfo> {

    // Newer files first
    @Override
    public int compareTo(FileInfo o) {
        return Integer.compare(o.fileId, this.fileId);
    }
}
