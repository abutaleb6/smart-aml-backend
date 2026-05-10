package com.smartaml.infrastructure.storage.local;

import com.smartaml.infrastructure.storage.StorageProperties;
import com.smartaml.infrastructure.storage.StorageService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
public class LocalStorageService implements StorageService {

    private final StorageProperties props;

    public LocalStorageService(StorageProperties props) {
        this.props = props;
    }

    @PostConstruct
    public void init() throws IOException {
        Files.createDirectories(Path.of(props.getLocalPath()));
    }

    @Override
    public String store(MultipartFile file, java.util.UUID tenantId, String folder) throws IOException {
        String sanitized = file.getOriginalFilename() == null ? "file" : file.getOriginalFilename().replaceAll("[^a-zA-Z0-9.\\-]","_");
        String filename = UUID.randomUUID() + "_" + sanitized;
        Path tenantDir = Path.of(props.getLocalPath(), tenantId.toString(), folder == null ? "" : folder);
        Files.createDirectories(tenantDir);
        Path dest = tenantDir.resolve(filename);
        file.transferTo(dest);
        String relative = tenantId + "/" + (folder == null ? "" : folder + "/") + filename;
        return relative;
    }

    @Override
    public StorageResource retrieve(String relativePath) throws IOException {
        Path full = Path.of(props.getLocalPath(), relativePath);
        if (!Files.exists(full)) throw new java.io.FileNotFoundException("No file: " + relativePath);
        InputStream in = new FileInputStream(full.toFile());
        String contentType = Files.probeContentType(full);
        long size = Files.size(full);
        return new StorageResource(in, contentType, size, full.getFileName().toString());
    }

    @Override
    public void delete(String relativePath) {
        try { Files.deleteIfExists(Path.of(props.getLocalPath(), relativePath)); } catch (Exception ignored) {}
    }

    @Override
    public String getPublicUrl(String relativePath) { return props.getBaseUrl() + "/api/v1/files/" + relativePath; }
}
