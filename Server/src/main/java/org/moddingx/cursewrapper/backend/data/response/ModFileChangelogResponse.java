package org.moddingx.cursewrapper.backend.data.response;

import com.google.gson.annotations.Expose;
import org.moddingx.cursewrapper.backend.CurseData;

// mods/{modId}/files/{fileId}/changelog
public class ModFileChangelogResponse implements CurseData {
    
    @Expose public String data;

    public ModFileChangelogResponse(String data) {
        this.data = data;
    }
}
