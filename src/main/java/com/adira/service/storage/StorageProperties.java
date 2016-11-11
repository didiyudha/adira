package com.adira.service.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by didi-realtime on 04/11/16.
 */
@ConfigurationProperties("storage")
public class StorageProperties {

    /**
     * Folder to store files
     */
    private String uploadPath = "upload-dir";
    private String auditeePath = "auditee-dir";

    public String getAuditeePath() {
        return auditeePath;
    }

    public void setAuditeePath(String auditeePath) {
        this.auditeePath = auditeePath;
    }

    public String getUploadPath() {
        return uploadPath;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }
}
