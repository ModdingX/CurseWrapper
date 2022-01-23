package io.github.noeppi_noeppi.tools.cursewrapper;

import io.github.noeppi_noeppi.tools.cursewrapper.backend.CurseApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Service;

import java.nio.file.Path;

public class CurseServer {
    
    private static final Logger logger = LoggerFactory.getLogger(CurseServer.class);
    
    private final Service spark;
    
    public CurseServer(int port, SslData ssl, CurseApi api, int threads) {
        logger.info("Starting Server on port {}.", port);
        this.spark = Service.ignite();
        this.spark.port(port);
        this.spark.threadPool(threads);
        if (ssl != null) {
            this.spark.secure(ssl.cert().toAbsolutePath().normalize().toString(), ssl.key(), null, null);
        } else {
            logger.warn("Running without SSL.");
        }
        
        // Query args are non-mandatory
        
        // Search mods, empty term = featured mods
        // max 30 results, discard others as never needed
        // route: search?loader=...&version=...&query=...
        // result:  List<ProjectInfo>
        
        // Project slug
        // route: slug/{projectId}
        // result: Just a string, no json data
        
        // Project info
        // route: project/{projectId}
        // result: ProjectInfo
        //   slug
        //   name
        //   websiteUrl
        //   thumbnailUrl
        //   
        
        // All files for project + filter by game version, loader
        // route: project/{projectId}/files?loader=...&version=...
        // result: List<FileInfo>
        
        // File Info
        // route: project/{projectId}/file/{fileId}
        // result: FileInfo
        //   projectId
        //   fileId
        //   fileName
        //   loader
        //   game version
        //   release type
        //   fileDate
        //   dependencies
        //   
        
        // File Changelog
        // route: project/{projectId}/changelog/{fileId}
        // result: Just a string, no json data
        
        // TODO routes
        
        this.spark.awaitInitialization();
    }
    
    public void shutdown() {
        this.spark.stop();
    }
    
    public record SslData(String key, Path cert) {}
}
