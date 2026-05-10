package com.smartaml.infrastructure.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private String frontendUrl;
    private String superAdminEmail;
    private StorageProperties storage;

    public String getFrontendUrl() { return frontendUrl; }
    public void setFrontendUrl(String frontendUrl) { this.frontendUrl = frontendUrl; }
    public String getSuperAdminEmail() { return superAdminEmail; }
    public void setSuperAdminEmail(String superAdminEmail) { this.superAdminEmail = superAdminEmail; }
    public StorageProperties getStorage() { return storage; }
    public void setStorage(StorageProperties storage) { this.storage = storage; }
}
