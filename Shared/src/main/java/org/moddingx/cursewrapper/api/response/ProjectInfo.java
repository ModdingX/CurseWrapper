package org.moddingx.cursewrapper.api.response;

import java.net.URI;

public record ProjectInfo(
        int projectId,
        String slug,
        String name,
        String owner,
        String summary,
        boolean distribution,
        URI website,
        URI thumbnail
) {}
