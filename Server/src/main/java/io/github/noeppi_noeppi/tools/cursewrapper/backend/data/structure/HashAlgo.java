package io.github.noeppi_noeppi.tools.cursewrapper.backend.data.structure;

import javax.annotation.Nullable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public enum HashAlgo {
    
    OTHER("other"),
    SHA1("sha1"),
    MD5("md5");
    
    public final String id;

    HashAlgo(String id) {
        this.id = id;
    }
    
    @Nullable
    public MessageDigest createDigest() {
        if (this == OTHER) return null;
        try {
            return MessageDigest.getInstance(this.name());
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
}
