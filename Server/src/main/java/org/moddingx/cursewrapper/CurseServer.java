package org.moddingx.cursewrapper;

import org.moddingx.cursewrapper.cache.CurseCache;
import org.moddingx.cursewrapper.route.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Service;

import java.nio.file.Path;

public class CurseServer {
    
    private static final Logger logger = LoggerFactory.getLogger(CurseServer.class);
    
    private final String version;
    private final Service spark;
    
    public CurseServer(String version, int port, SslData ssl, int threads, CurseCache cache) {
        logger.info("Starting Server on port {}.", port);
        this.version = version;
        this.spark = Service.ignite();
        this.spark.port(port);
        logger.info("Running on {} threads.", threads);
        this.spark.threadPool(threads, threads, -1);
        if (ssl != null) {
            this.spark.secure(ssl.cert().toAbsolutePath().normalize().toString(), ssl.key(), null, null);
        } else {
            logger.warn("Running without SSL.");
        }
        
        this.spark.get("/version", new VersionRoute(this.spark, cache, version));
        this.spark.get("/search", new SearchRoute(this.spark, cache));
        this.spark.get("/slug/:projectId", new SlugRoute(this.spark, cache));
        this.spark.get("/project/:projectId", new ProjectRoute(this.spark, cache));
        this.spark.get("/project/:projectId/files", new FilesRoute(this.spark, cache));
        this.spark.get("/project/:projectId/latest", new LatestFileRoute(this.spark, cache));
        this.spark.get("/project/:projectId/file/:fileId", new FileRoute(this.spark, cache));
        this.spark.get("/project/:projectId/changelog/:fileId", new ChangelogRoute(this.spark, cache));
        this.spark.get("/projects", new ProjectsRoute(this.spark, cache));
        
        this.spark.awaitInitialization();
        logger.info("Server started.");
    }

    public String version() {
        return version;
    }

    public void shutdown() {
        this.spark.stop();
    }
    
    public record SslData(String key, Path cert) {}
}
