package com.smartaml.infrastructure.storage.hybrid;

import com.smartaml.infrastructure.storage.StorageProperties;
import com.smartaml.infrastructure.storage.StorageService;
import com.smartaml.infrastructure.storage.local.LocalStorageService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.UUID;

@Primary
@Service
public class HybridStorageService implements StorageService {

    private final LocalStorageService local;
    private final StorageProperties props;
    private final StorageSyncQueue queue;

    public HybridStorageService(LocalStorageService local, StorageProperties props, StorageSyncQueue queue) {
        this.local = local;
        this.props = props;
        this.queue = queue;
    }

    @Override
    public String store(MultipartFile file, UUID tenantId, String folder) throws IOException {
        String relative = local.store(file, tenantId, folder);
        if (props.isS3Enabled()) {
            queue.enqueue(relative);
        }
        return relative;
    }

    @Override
    public StorageResource retrieve(String relativePath) throws IOException {
        // Try local then S3 (S3 implementation not included here)
        try {
            return local.retrieve(relativePath);
        } catch (IOException e) {
            throw e;
        }
    }

    @Override
    public void delete(String relativePath) {
        local.delete(relativePath);
        // s3 delete would be invoked asynchronously
    }

    @Override
    public String getPublicUrl(String relativePath) {
        return local.getPublicUrl(relativePath);
    }
}
