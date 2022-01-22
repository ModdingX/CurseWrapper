package io.github.noeppi_noeppi.tools.cursewrapper;

import joptsimple.OptionParser;
import joptsimple.OptionSpec;
import joptsimple.util.PathConverter;
import joptsimple.util.PathProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        OptionParser options = new OptionParser(false);
        OptionSpec<Void> specDocker = options.accepts("docker", "Run in Docker mode. This will load secrets as docker secrets.");
        
        OptionSpec<String> specToken = options.accepts("token", "The CurseForge API token to use")
                .availableUnless(specDocker).withRequiredArg();

        OptionSpec<Void> specNoSsl = options.accepts("no-ssl", "Disable SSL. For testing only.");

        OptionSpec<Path> specSsl = options.accepts("ssl", "The SSL certificate fle to use.")
                .availableUnless(specDocker, specNoSsl).withRequiredArg()
                .withValuesConvertedBy(new PathConverter(PathProperties.FILE_EXISTING, PathProperties.READABLE));
        
        OptionSpec<String> specSslKey = options.accepts("ssl-key", "The password of the SSL certificate.")
                .availableUnless(specDocker, specNoSsl).withRequiredArg().defaultsTo("");
        
        OptionSpec<Integer> specPort = options.accepts("port", "The port to run on.").withRequiredArg().ofType(Integer.class);
        
        
    }
}
