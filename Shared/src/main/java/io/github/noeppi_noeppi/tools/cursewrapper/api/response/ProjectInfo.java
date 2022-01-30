package io.github.noeppi_noeppi.tools.cursewrapper.api.response;

import java.net.URI;
import java.util.Objects;

public final class ProjectInfo {

    private final int projectId;
    private final String slug;
    private final String name;
    private final String owner;
    private final String summary;
    private final URI website;
    private final URI thumbnail;

    public ProjectInfo(
            int projectId,
            String slug,
            String name,
            String owner,
            String summary,
            URI website,
            URI thumbnail
    ) {
        this.projectId = projectId;
        this.slug = slug;
        this.name = name;
        this.owner = owner;
        this.summary = summary;
        this.website = website;
        this.thumbnail = thumbnail;
    }

    public int projectId() {
        return projectId;
    }

    public String slug() {
        return slug;
    }

    public String name() {
        return name;
    }

    public String owner() {
        return owner;
    }

    public String summary() {
        return summary;
    }

    public URI website() {
        return website;
    }

    public URI thumbnail() {
        return thumbnail;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (ProjectInfo) obj;
        return this.projectId == that.projectId &&
                Objects.equals(this.slug, that.slug) &&
                Objects.equals(this.name, that.name) &&
                Objects.equals(this.owner, that.owner) &&
                Objects.equals(this.summary, that.summary) &&
                Objects.equals(this.website, that.website) &&
                Objects.equals(this.thumbnail, that.thumbnail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId, slug, name, owner, summary, website, thumbnail);
    }

    @Override
    public String toString() {
        return "ProjectInfo[" +
                "projectId=" + projectId + ", " +
                "slug=" + slug + ", " +
                "name=" + name + ", " +
                "owner=" + owner + ", " +
                "summary=" + summary + ", " +
                "website=" + website + ", " +
                "thumbnail=" + thumbnail + ']';
    }
}
