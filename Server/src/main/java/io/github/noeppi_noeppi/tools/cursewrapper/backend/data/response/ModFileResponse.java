package io.github.noeppi_noeppi.tools.cursewrapper.backend.data.response;

import com.google.gson.annotations.Expose;
import io.github.noeppi_noeppi.tools.cursewrapper.backend.CurseData;
import io.github.noeppi_noeppi.tools.cursewrapper.backend.data.structure.FileStatus;
import io.github.noeppi_noeppi.tools.cursewrapper.backend.data.structure.HashAlgo;
import io.github.noeppi_noeppi.tools.cursewrapper.backend.data.structure.ModRelationType;
import io.github.noeppi_noeppi.tools.cursewrapper.backend.data.structure.ModReleaseType;

import java.util.Date;
import java.util.List;

// mods/{modId}/files/{fileId}
public class ModFileResponse implements CurseData {
    
    @Expose public ModFile data;

    public ModFileResponse(ModFile data) {
        this.data = data;
    }

    public static class ModFile {
        
        @Expose public int id;
        @Expose public int gameId;
        @Expose public int modId;
        @Expose public boolean isAvailable;
        @Expose public String displayName;
        @Expose public String fileName;
        @Expose public ModReleaseType releaseType;
        @Expose public FileStatus fileStatus;
        @Expose public List<FileHash> hashes;
        @Expose public Date fileDate;
        @Expose public int fileLength;
        @Expose public int downloadCount;
        @Expose public String downloadUrl;
        @Expose public List<String> gameVersions;
        // sortableGameVersions
        @Expose public List<Dependency> dependencies;
        @Expose public int parentProjectFileId;
        @Expose public int alternateFileId;
        @Expose public boolean isServerPack;
        @Expose public int serverPackFileId;
        @Expose public int fileFingerprint;

        public ModFile(int id, int gameId, int modId, boolean isAvailable, String displayName, String fileName, ModReleaseType releaseType, FileStatus fileStatus, List<FileHash> hashes, Date fileDate, int fileLength, int downloadCount, String downloadUrl, List<String> gameVersions, List<Dependency> dependencies, int parentProjectFileId, int alternateFileId, boolean isServerPack, int serverPackFileId, int fileFingerprint) {
            this.id = id;
            this.gameId = gameId;
            this.modId = modId;
            this.isAvailable = isAvailable;
            this.displayName = displayName;
            this.fileName = fileName;
            this.releaseType = releaseType;
            this.fileStatus = fileStatus;
            this.hashes = hashes;
            this.fileDate = fileDate;
            this.fileLength = fileLength;
            this.downloadCount = downloadCount;
            this.downloadUrl = downloadUrl;
            this.gameVersions = gameVersions;
            this.dependencies = dependencies;
            this.parentProjectFileId = parentProjectFileId;
            this.alternateFileId = alternateFileId;
            this.isServerPack = isServerPack;
            this.serverPackFileId = serverPackFileId;
            this.fileFingerprint = fileFingerprint;
        }
    }
    
    public static class FileHash {
        
        @Expose public HashAlgo algo;
        @Expose public String value;

        public FileHash(HashAlgo algo, String value) {
            this.algo = algo;
            this.value = value;
        }
    }
    
    public static class Dependency {
        
        @Expose public int modId;
        @Expose public int fileId;
        @Expose public ModRelationType relationType;

        public Dependency(int modId, int fileId, ModRelationType relationType) {
            this.modId = modId;
            this.fileId = fileId;
            this.relationType = relationType;
        }
    }
}
