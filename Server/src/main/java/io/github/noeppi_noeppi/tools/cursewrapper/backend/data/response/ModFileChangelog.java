package io.github.noeppi_noeppi.tools.cursewrapper.backend.data.response;

import com.google.gson.annotations.Expose;
import io.github.noeppi_noeppi.tools.cursewrapper.backend.CurseData;

// mods/{modId}/files/{fileId}/changelog
public class ModFileChangelog implements CurseData {
    
    @Expose public String data;

    public ModFileChangelog(String data) {
        this.data = data;
    }
}
