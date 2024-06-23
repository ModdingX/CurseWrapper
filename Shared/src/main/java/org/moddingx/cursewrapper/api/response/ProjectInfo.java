package org.moddingx.cursewrapper.api.response;

import java.net.URI;
import java.util.List;

public record ProjectInfo(
        int projectId,
        String slug,
        String name,
        String owner,
        String summary,
        int downloadCount,
        List<String> gameVersions,
        boolean distribution,
        URI website,
        URI thumbnail
) {}
