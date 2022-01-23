package io.github.noeppi_noeppi.tools.cursewrapper.api.response;

import java.net.URI;

public record ProjectInfo(
        int projectId,
        String slug,
        String name,
        String owner,
        String summary,
        URI website,
        URI thumbnail
) {}
