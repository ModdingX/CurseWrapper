package org.moddingx.cursewrapper.backend.data.request;

import com.google.gson.annotations.Expose;
import org.moddingx.cursewrapper.backend.CurseData;

import java.util.List;

// fingerprints
public class FingerprintRequest implements CurseData {

    @Expose public List<Long> fingerprints;

    public FingerprintRequest(List<Long> fingerprints) {
        this.fingerprints = fingerprints;
    }
}
