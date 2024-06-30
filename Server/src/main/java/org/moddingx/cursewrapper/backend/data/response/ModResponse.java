package org.moddingx.cursewrapper.backend.data.response;

import com.google.gson.annotations.Expose;
import jakarta.annotation.Nullable;
import org.moddingx.cursewrapper.backend.CurseData;
import org.moddingx.cursewrapper.backend.data.structure.ModLoaderType;
import org.moddingx.cursewrapper.backend.data.structure.ModReleaseType;
import org.moddingx.cursewrapper.backend.data.structure.ModStatus;

import java.util.Date;
import java.util.List;

public class ModResponse implements CurseData {
    
    @Expose public Mod data;

    public ModResponse(Mod data) {
        this.data = data;
    }

    public static class Mod {
        
        @Expose public int id;
        @Expose public int gameId;
        @Expose public String slug;
        @Expose public String name;
        @Expose public Links links;
        @Expose public String summary;
        @Expose public ModStatus status;
        @Expose public int downloadCount;
        @Expose public boolean isFeatured;
        @Expose public int primaryCategoryId;
        @Expose public List<CategoriesResponse.Category> categories;
        @Expose public List<Author> authors;
        @Expose @Nullable public Logo logo;
        @Expose public List<Screenshot> screenshots;
        @Expose public int mainFileId;
        @Expose public List<ModFileResponse.ModFile> latestFiles;
        @Expose public List<LatestFileIndex> latestFilesIndexes;
        @Expose public Date dateCreated;
        @Expose public Date dateModified;
        @Expose public Date dateReleased;
        @Expose @Nullable public Boolean allowModDistribution;

        public Mod(int id, int gameId, String slug, String name, Links links, String summary, ModStatus status, int downloadCount, boolean isFeatured, int primaryCategoryId, List<CategoriesResponse.Category> categories, List<Author> authors, @Nullable Logo logo, List<Screenshot> screenshots, int mainFileId, List<ModFileResponse.ModFile> latestFiles, List<LatestFileIndex> latestFilesIndexes, Date dateCreated, Date dateModified, Date dateReleased, boolean allowModDistribution) {
            this.id = id;
            this.gameId = gameId;
            this.slug = slug;
            this.name = name;
            this.links = links;
            this.summary = summary;
            this.status = status;
            this.downloadCount = downloadCount;
            this.isFeatured = isFeatured;
            this.primaryCategoryId = primaryCategoryId;
            this.categories = categories;
            this.authors = authors;
            this.logo = logo;
            this.screenshots = screenshots;
            this.mainFileId = mainFileId;
            this.latestFiles = latestFiles;
            this.latestFilesIndexes = latestFilesIndexes;
            this.dateCreated = dateCreated;
            this.dateModified = dateModified;
            this.dateReleased = dateReleased;
            this.allowModDistribution = allowModDistribution;
        }
    }
    
    public static class Links {
        
        @Expose public String websiteUrl;
        @Expose public String wikiUrl;
        @Expose public String issuesUrl;
        @Expose public String sourceUrl;

        public Links(String websiteUrl, String wikiUrl, String issuesUrl, String sourceUrl) {
            this.websiteUrl = websiteUrl;
            this.wikiUrl = wikiUrl;
            this.issuesUrl = issuesUrl;
            this.sourceUrl = sourceUrl;
        }
    }
    
    public static class Author {
        
        @Expose public int id;
        @Expose public String name;
        @Expose public String url;

        public Author(int id, String name, String url) {
            this.id = id;
            this.name = name;
            this.url = url;
        }
    }
    
    public static class Logo {
        
        @Expose public int id;
        @Expose public int modid;
        @Expose public String title;
        @Expose public String description;
        @Expose public String thumbnailUrl;
        @Expose public String url;

        public Logo(int id, int modid, String title, String description, String thumbnailUrl, String url) {
            this.id = id;
            this.modid = modid;
            this.title = title;
            this.description = description;
            this.thumbnailUrl = thumbnailUrl;
            this.url = url;
        }
    }
    
    public static class Screenshot {
        
        @Expose public int id;
        @Expose public int modId;
        @Expose public String title;
        @Expose public String description;
        @Expose public String thumbnailUrl;
        @Expose public String url;

        public Screenshot(int id, int modId, String title, String description, String thumbnailUrl, String url) {
            this.id = id;
            this.modId = modId;
            this.title = title;
            this.description = description;
            this.thumbnailUrl = thumbnailUrl;
            this.url = url;
        }
    }
    
    public static class LatestFileIndex {
        
        @Expose public String gameVersion;
        @Expose public int fileId;
        @Expose public String filename;
        @Expose public ModReleaseType releaseType;
        @Expose public int gameVersionTypeId;
        @Expose public ModLoaderType modLoader;

        public LatestFileIndex(String gameVersion, int fileId, String filename, ModReleaseType releaseType, int gameVersionTypeId, ModLoaderType modLoader) {
            this.gameVersion = gameVersion;
            this.fileId = fileId;
            this.filename = filename;
            this.releaseType = releaseType;
            this.gameVersionTypeId = gameVersionTypeId;
            this.modLoader = modLoader;
        }
    }
}
