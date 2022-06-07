package org.moddingx.cursewrapper;

import org.moddingx.cursewrapper.backend.CurseApi;
import org.moddingx.cursewrapper.cache.CurseCache;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import joptsimple.util.PathConverter;
import joptsimple.util.PathProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.jar.Manifest;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws IOException {
        String version = null;
        try(InputStream in = Main.class.getResourceAsStream("/META-INF/MANIFEST.MF")) {
            if (in != null) {
                Manifest manifest = new Manifest(in);
                version = manifest.getMainAttributes().getValue("Implementation-Version");
            }
        } catch (Exception e) {
            //
        }
        
        OptionParser options = new OptionParser(false);
        OptionSpec<Void> specDocker = options.accepts("docker", "Run in Docker mode. This will load secrets as docker secrets.");

        OptionSpec<String> specToken = options.accepts("token", "The CurseForge API token to use")
                .availableUnless(specDocker).requiredUnless(specDocker).withRequiredArg();

        OptionSpec<Void> specNoSsl = options.accepts("no-ssl", "Disable SSL. For testing only.");

        OptionSpec<Path> specSsl = options.accepts("ssl", "The SSL certificate fle to use.")
                .availableUnless(specDocker, specNoSsl).withRequiredArg()
                .withValuesConvertedBy(new PathConverter(PathProperties.FILE_EXISTING, PathProperties.READABLE));

        OptionSpec<String> specSslKey = options.accepts("ssl-key", "The password of the SSL certificate.")
                .availableUnless(specDocker, specNoSsl).withRequiredArg().defaultsTo("");

        OptionSpec<Integer> specPort = options.accepts("port", "The port to run on.").withRequiredArg().ofType(Integer.class);
        OptionSpec<Integer> specThreads = options.accepts("threads", "How many threads the server should use.")
                .withRequiredArg().ofType(Integer.class).defaultsTo(Math.min(4, Runtime.getRuntime().availableProcessors()));

        OptionSet set = options.parse(args);
        
        if (version == null) {
            logger.warn("Failed to detect version, falling back to 'UNKNOWN'");
            version = "UNKNOWN";
        } else {
            logger.info("Running CurseWrapper v" + version);
        }

        boolean docker = set.has(specDocker);
        boolean useSsl = !set.has(specNoSsl);

        String token;
        CurseServer.SslData ssl = null;
        int port = set.has(specPort) ? set.valueOf(specPort) : (useSsl ? 443 : 80);

        if (docker) {
            token = dockerSecret("curse_token");
            if (useSsl) {
                ssl = new CurseServer.SslData(dockerSecret("ssl_keystore_password"), dockerSecretPath("ssl_keystore"));
            }
        } else {
            token = set.valueOf(specToken);
            if (useSsl) {
                ssl = new CurseServer.SslData(set.valueOf(specSslKey), set.valueOf(specSsl).toAbsolutePath().normalize());
            }
        }

        CurseApi api = new CurseApi(token);
        api.testToken();
        CurseCache cache = new CurseCache(api);
        CurseServer server = new CurseServer(version, port, ssl, set.valueOf(specThreads), cache);
        Runtime.getRuntime().addShutdownHook(new Thread(server::shutdown));
        logger.info("Initialisation done.");
    }

    private static String dockerSecret(String id) throws IOException {
        return Files.readString(dockerSecretPath(id));
    }
    
    private static Path dockerSecretPath(String id) {
        return Paths.get("/", "run", "secrets", id);
    }
}
