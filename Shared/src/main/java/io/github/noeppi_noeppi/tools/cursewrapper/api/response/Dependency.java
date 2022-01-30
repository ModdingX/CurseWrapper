package io.github.noeppi_noeppi.tools.cursewrapper.api.response;

import java.util.Objects;

public final class Dependency {

    private final RelationType type;
    private final int projectId;

    public Dependency(
            RelationType type,
            int projectId
    ) {
        this.type = type;
        this.projectId = projectId;
    }

    public RelationType type() {
        return type;
    }

    public int projectId() {
        return projectId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Dependency) obj;
        return Objects.equals(this.type, that.type) &&
                this.projectId == that.projectId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, projectId);
    }

    @Override
    public String toString() {
        return "Dependency[" +
                "type=" + type + ", " +
                "projectId=" + projectId + ']';
    }
}
