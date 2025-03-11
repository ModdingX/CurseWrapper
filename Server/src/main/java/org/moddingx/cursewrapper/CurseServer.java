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
    
    public CurseServer(String version, int port, SslData ssl, CurseCache cache) {
        logger.info("Starting Server on port {}.", port);
        this.version = version;
        this.spark = Service.ignite();
        this.spark.port(port);
        this.spark.withVirtualThread();
        if (ssl != null) {
            this.spark.secure(ssl.cert().toAbsolutePath().normalize().toString(), ssl.key(), null, null);
        } else {
            logger.warn("Running without SSL.");
        }

        // Support trailing slashes
        this.spark.before((req, res) -> {
            String path = req.servletPath();
            String pathExtra = req.pathInfo();
            if (path != null && pathExtra != null) path = path + pathExtra;
            if (path != null && path.length() > 1 && path.endsWith("/")) {
                res.redirect(path.substring(0, path.length() - 1));
            }
        });
        
        this.spark.get("/version", new VersionRoute(this.spark, cache, version));
        this.spark.get("/search", new SearchRoute(this.spark, cache));
        this.spark.get("/slug/:projectId", new SlugRoute(this.spark, cache));
        this.spark.get("/project/:projectId", new ProjectRoute(this.spark, cache));
        this.spark.get("/project/:projectId/files", new FilesRoute(this.spark, cache));
        this.spark.get("/project/:projectId/latest", new LatestFileRoute(this.spark, cache));
        this.spark.get("/project/:projectId/file/:fileId", new FileRoute(this.spark, cache));
        this.spark.get("/project/:projectId/changelog/:fileId", new ChangelogRoute(this.spark, cache));
        this.spark.get("/projects", new ProjectsRoute(this.spark, cache));
        this.spark.get("/fingerprints", new FingerprintsRoute(this.spark, cache));
        
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
