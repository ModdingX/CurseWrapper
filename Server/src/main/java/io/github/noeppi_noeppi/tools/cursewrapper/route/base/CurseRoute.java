package io.github.noeppi_noeppi.tools.cursewrapper.route.base;

import io.github.noeppi_noeppi.tools.cursewrapper.cache.CurseCache;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.function.Function;

public abstract class CurseRoute<T> implements Route {
    
    protected final Service spark;
    protected final CurseCache cache;
    private final String content;
    private final Function<T, String> resultFunc;

    protected CurseRoute(Service spark, CurseCache cache, String content, Function<T, String> resultFunc) {
        this.spark = spark;
        this.cache = cache;
        this.content = content;
        this.resultFunc = resultFunc;
    }

    @Override
    public final Object handle(Request request, Response response) throws Exception {
        try {
            RouteData data = new RouteData();
            String result = this.resultFunc.apply(this.apply(request, response, data));
            response.status(result == null ? 204 : 200);
            if (result != null) {
                response.header("Content-Type", this.content);
                if (!data.cache) {
                    response.header("Cache-Control", "no-cache");
                }
            }
            return result;
        } catch (FileNotFoundException e) {
            throw this.spark.halt(404, "Not Found");
        }
    }
    
    protected abstract T apply(Request request, Response response, RouteData route) throws IOException;
    
    protected final int integer(Request request, String key) {
        try {
            return Integer.parseInt(this.param(request, key));
        } catch (NumberFormatException e) {
            throw this.spark.halt(400, "Invalid " + key);
        }
    }
    
    protected final String param(Request request, String key) {
        String value = request.params(":" + key);
        if (value == null) {
            throw this.spark.halt(500, "Missing key: " + key);
        }
        return value;
    }
    
    public static class RouteData {
        
        private boolean cache;
        
        private RouteData() {
            this.cache = false;
        }
        
        public void allowCaching() {
            this.cache = true;
        }
    }
}

