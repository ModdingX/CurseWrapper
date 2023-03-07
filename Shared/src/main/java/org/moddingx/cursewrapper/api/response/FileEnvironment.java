package org.moddingx.cursewrapper.api.response;

import java.util.Locale;

public enum FileEnvironment {
    
    CLIENT(true, false),
    SERVER(false, true),
    BOTH(true, true);
    
    public final String id;
    public final boolean client;
    public final boolean server;

    FileEnvironment(boolean client, boolean server) {
        this.id = this.name().toLowerCase(Locale.ROOT);
        this.client = client;
        this.server = server;
    }

    public static FileEnvironment get(String id) {
        try {
            return FileEnvironment.valueOf(id.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            return BOTH;
        }
    }
}
