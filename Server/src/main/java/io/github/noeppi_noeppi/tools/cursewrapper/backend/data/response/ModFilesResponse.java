package io.github.noeppi_noeppi.tools.cursewrapper.backend.data.response;

import com.google.gson.annotations.Expose;
import io.github.noeppi_noeppi.tools.cursewrapper.backend.CurseData;
import io.github.noeppi_noeppi.tools.cursewrapper.backend.data.util.Pagination;

import java.util.List;

// mods/{modId}/files
public class ModFilesResponse implements CurseData {

    @Expose public List<ModFileResponse.ModFile> data;
    @Expose public Pagination pagination;

    public ModFilesResponse(List<ModFileResponse.ModFile> data, Pagination pagination) {
        this.data = data;
        this.pagination = pagination;
    }
}
