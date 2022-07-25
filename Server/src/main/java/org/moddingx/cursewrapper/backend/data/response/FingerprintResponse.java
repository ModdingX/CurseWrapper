package org.moddingx.cursewrapper.backend.data.response;

import com.google.gson.annotations.Expose;
import org.moddingx.cursewrapper.backend.CurseData;

import java.util.List;

public class FingerprintResponse implements CurseData {
    
    @Expose public FingerprintData data;

    public FingerprintResponse(FingerprintData data) {
        this.data = data;
    }

    public static class FingerprintData {
        
        @Expose public boolean isCacheBuilt;
        @Expose public List<FingerprintMatch> exactMatches;
        @Expose public List<Long> exactFingerprints;
        @Expose public List<FingerprintMatch> partialMatches;
        // partialMatchFingerprints
        @Expose public List<Long> installedFingerprints;
        @Expose public List<Long> unmatchedFingerprints;

        public FingerprintData(boolean isCacheBuilt, List<FingerprintMatch> exactMatches, List<Long> exactFingerprints, List<FingerprintMatch> partialMatches, List<Long> installedFingerprints, List<Long> unmatchedFingerprints) {
            this.isCacheBuilt = isCacheBuilt;
            this.exactMatches = exactMatches;
            this.exactFingerprints = exactFingerprints;
            this.partialMatches = partialMatches;
            this.installedFingerprints = installedFingerprints;
            this.unmatchedFingerprints = unmatchedFingerprints;
        }
    }
    
    public static class FingerprintMatch {
        
        @Expose public long id;
        @Expose public ModFileResponse.ModFile file;
        @Expose public List<ModFileResponse.ModFile> latestFiles;

        public FingerprintMatch(long id, ModFileResponse.ModFile file, List<ModFileResponse.ModFile> latestFiles) {
            this.id = id;
            this.file = file;
            this.latestFiles = latestFiles;
        }
    }
}
