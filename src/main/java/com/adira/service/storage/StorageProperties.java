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
    private String pathLocation = "upload-dir";

    public String getPathLocation() {
        return pathLocation;
    }

    public void setPathLocation(String pathLocation) {
        this.pathLocation = pathLocation;
    }
}
