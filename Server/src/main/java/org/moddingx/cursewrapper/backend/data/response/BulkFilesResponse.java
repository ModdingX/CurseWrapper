package org.moddingx.cursewrapper.backend.data.response;

import com.google.gson.annotations.Expose;
import org.moddingx.cursewrapper.backend.CurseData;

import java.util.List;

public class BulkFilesResponse implements CurseData {
    
    @Expose public List<ModFileResponse.ModFile> data;

    public BulkFilesResponse(List<ModFileResponse.ModFile> data) {
        this.data = data;
    }
}
