package org.moddingx.cursewrapper.api.response;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

public final class FileInfo {

    private final int projectId;
    private final int fileId;
    private final String name;
    private final List<ModLoader> loader;
    private final List<String> gameVersions;
    private final ReleaseType releaseType;
    private final Instant fileDate;
    private final List<Dependency> dependencies;

    public FileInfo(
            int projectId,
            int fileId,
            String name,
            List<ModLoader> loader,
            List<String> gameVersions,
            ReleaseType releaseType,
            Instant fileDate,
            List<Dependency> dependencies
    ) {
        this.projectId = projectId;
        this.fileId = fileId;
        this.name = name;
        this.loader = loader;
        this.gameVersions = gameVersions;
        this.releaseType = releaseType;
        this.fileDate = fileDate;
        this.dependencies = dependencies;
    }

    public int projectId() {
        return projectId;
    }

    public int fileId() {
        return fileId;
    }

    public String name() {
        return name;
    }

    public List<ModLoader> loader() {
        return loader;
    }

    public List<String> gameVersions() {
        return gameVersions;
    }

    public ReleaseType releaseType() {
        return releaseType;
    }

    public Instant fileDate() {
        return fileDate;
    }

    public List<Dependency> dependencies() {
        return dependencies;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (FileInfo) obj;
        return this.projectId == that.projectId &&
                this.fileId == that.fileId &&
                Objects.equals(this.name, that.name) &&
                Objects.equals(this.loader, that.loader) &&
                Objects.equals(this.gameVersions, that.gameVersions) &&
                Objects.equals(this.releaseType, that.releaseType) &&
                Objects.equals(this.fileDate, that.fileDate) &&
                Objects.equals(this.dependencies, that.dependencies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId, fileId, name, loader, gameVersions, releaseType, fileDate, dependencies);
    }

    @Override
    public String toString() {
        return "FileInfo[" +
                "projectId=" + projectId + ", " +
                "fileId=" + fileId + ", " +
                "name=" + name + ", " +
                "loader=" + loader + ", " +
                "gameVersions=" + gameVersions + ", " +
                "releaseType=" + releaseType + ", " +
                "fileDate=" + fileDate + ", " +
                "dependencies=" + dependencies + ']';
    }
}
