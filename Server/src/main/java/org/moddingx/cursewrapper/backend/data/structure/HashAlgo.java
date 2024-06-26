package org.moddingx.cursewrapper.backend.data.structure;

import jakarta.annotation.Nullable;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public enum HashAlgo implements CurseEnum {
    
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
