package org.moddingx.cursewrapper.backend.data.response;

import com.google.gson.annotations.Expose;
import org.moddingx.cursewrapper.backend.CurseData;

import java.util.Date;

// categories
public class CategoriesResponse implements CurseData {
    
    @Expose public Category data;

    public CategoriesResponse(Category data) {
        this.data = data;
    }

    public static class Category {
        
        @Expose public int id;
        @Expose public int gameId;
        @Expose public String slug;
        @Expose public String name;
        @Expose public String url;
        @Expose public String iconUrl;
        @Expose public Date dateModified;
        @Expose public boolean isClass;
        @Expose public int classId;
        @Expose public int parentCategoryId;

        public Category(int id, int gameId, String slug, String name, String url, String iconUrl, Date dateModified, boolean isClass, int classId, int parentCategoryId) {
            this.id = id;
            this.gameId = gameId;
            this.slug = slug;
            this.name = name;
            this.url = url;
            this.iconUrl = iconUrl;
            this.dateModified = dateModified;
            this.isClass = isClass;
            this.classId = classId;
            this.parentCategoryId = parentCategoryId;
        }
    }
}
