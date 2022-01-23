package io.github.noeppi_noeppi.tools.cursewrapper.api.response;

public record Dependency(
        RelationType type,
        int projectId
) {}
