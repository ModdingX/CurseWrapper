package org.moddingx.cursewrapper.backend.data.request;

import com.google.gson.annotations.Expose;
import org.moddingx.cursewrapper.backend.CurseData;

import java.util.List;

public class BulkFilesRequest implements CurseData {

    @Expose public List<Integer> fileIds;

    public BulkFilesRequest(List<Integer> fileIds) {
        this.fileIds = fileIds;
    }
}
