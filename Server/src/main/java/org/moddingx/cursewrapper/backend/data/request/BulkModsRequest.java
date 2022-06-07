package org.moddingx.cursewrapper.backend.data.request;

import com.google.gson.annotations.Expose;
import org.moddingx.cursewrapper.backend.CurseData;

import java.util.List;

public class BulkModsRequest implements CurseData {

    @Expose public List<Integer> modIds;

    public BulkModsRequest(List<Integer> modIds) {
        this.modIds = modIds;
    }
}
