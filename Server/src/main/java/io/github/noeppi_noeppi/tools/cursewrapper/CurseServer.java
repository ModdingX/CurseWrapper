package io.github.noeppi_noeppi.tools.cursewrapper;

import io.github.noeppi_noeppi.tools.cursewrapper.backend.CurseApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Service;

import java.nio.file.Path;

public class CurseServer {
    
    private static final Logger logger = LoggerFactory.getLogger(CurseServer.class);
    
    private final Service spark;
    
    public CurseServer(int port, SslData ssl, CurseApi api) {
        logger.info("Starting Server on port {}.", port);
        this.spark = Service.ignite();
        if (ssl != null) {
            this.spark.secure(ssl.cert().toAbsolutePath().normalize().toString(), ssl.key(), null, null);
        } else {
            logger.warn("Running without SSL.");
        }
        
        // TODO routes
        
        this.spark.awaitInitialization();
    }
    
    public void shutdown() {
        this.spark.stop();
    }
    
    public record SslData(String key, Path cert) {}
}
