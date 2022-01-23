package io.github.noeppi_noeppi.tools.cursewrapper.api.response;

import java.net.URI;
import java.util.Locale;

public record ProjectInfo(
        int projectId,
        String slug,
        String name,
        String owner,
        String summary,
        URI website,
        URI thumbnail
) implements Comparable<ProjectInfo> {

    @Override
    public int compareTo(ProjectInfo o) {
        return this.name.toLowerCase(Locale.ROOT).compareTo(o.name.toLowerCase(Locale.ROOT));
    }
}
