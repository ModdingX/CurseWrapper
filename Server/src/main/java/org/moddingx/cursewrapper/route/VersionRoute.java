package org.moddingx.cursewrapper.route;

import org.moddingx.cursewrapper.cache.CurseCache;
import org.moddingx.cursewrapper.route.base.TextRoute;
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
