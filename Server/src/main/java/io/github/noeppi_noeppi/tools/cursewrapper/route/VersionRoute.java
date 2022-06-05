package io.github.noeppi_noeppi.tools.cursewrapper.route;

import io.github.noeppi_noeppi.tools.cursewrapper.cache.CurseCache;
import io.github.noeppi_noeppi.tools.cursewrapper.route.base.TextRoute;
import spark.Request;
import spark.Response;
import spark.Service;

import java.io.IOException;

public class VersionRoute extends TextRoute {

    private final String version;
    
    public VersionRoute(Service spark, CurseCache cache, String version) {
        super(spark, cache);
        this.version = version;
    }

    @Override
    protected String apply(Request request, Response response, RouteData route) throws IOException {
        route.allowCaching();
        return this.version;
    }
}
