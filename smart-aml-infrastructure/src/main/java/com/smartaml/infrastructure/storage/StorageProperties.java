package com.smartaml.infrastructure.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.storage")
public class StorageProperties {
    private String localPath;
    private String baseUrl;
    private String s3Endpoint;
    private String s3Region;
    private String s3Bucket;
    private String s3AccessKey;
    private String s3SecretKey;
    private String s3PublicUrl;
    private boolean s3Enabled;
    private int syncRetryMaxAttempts = 3;
    private long syncRetryDelayMs = 5000;

    // getters and setters

    public String getLocalPath() { return localPath; }
    public void setLocalPath(String localPath) { this.localPath = localPath; }
    public String getBaseUrl() { return baseUrl; }
    public void setBaseUrl(String baseUrl) { this.baseUrl = baseUrl; }
    public String getS3Endpoint() { return s3Endpoint; }
    public void setS3Endpoint(String s3Endpoint) { this.s3Endpoint = s3Endpoint; }
    public String getS3Region() { return s3Region; }
    public void setS3Region(String s3Region) { this.s3Region = s3Region; }
    public String getS3Bucket() { return s3Bucket; }
    public void setS3Bucket(String s3Bucket) { this.s3Bucket = s3Bucket; }
    public String getS3AccessKey() { return s3AccessKey; }
    public void setS3AccessKey(String s3AccessKey) { this.s3AccessKey = s3AccessKey; }
    public String getS3SecretKey() { return s3SecretKey; }
    public void setS3SecretKey(String s3SecretKey) { this.s3SecretKey = s3SecretKey; }
    public String getS3PublicUrl() { return s3PublicUrl; }
    public void setS3PublicUrl(String s3PublicUrl) { this.s3PublicUrl = s3PublicUrl; }
    public boolean isS3Enabled() { return s3Enabled; }
    public void setS3Enabled(boolean s3Enabled) { this.s3Enabled = s3Enabled; }
    public int getSyncRetryMaxAttempts() { return syncRetryMaxAttempts; }
    public void setSyncRetryMaxAttempts(int syncRetryMaxAttempts) { this.syncRetryMaxAttempts = syncRetryMaxAttempts; }
    public long getSyncRetryDelayMs() { return syncRetryDelayMs; }
    public void setSyncRetryDelayMs(long syncRetryDelayMs) { this.syncRetryDelayMs = syncRetryDelayMs; }
}
