package com.smartaml.infrastructure.storage;

import java.io.IOException;
import java.io.InputStream;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    String store(MultipartFile file, java.util.UUID tenantId, String folder) throws IOException;
    StorageResource retrieve(String relativePath) throws IOException;
    void delete(String relativePath);
    String getPublicUrl(String relativePath);

    record StorageResource(InputStream stream, String contentType, long contentLength, String filename) {}
}
