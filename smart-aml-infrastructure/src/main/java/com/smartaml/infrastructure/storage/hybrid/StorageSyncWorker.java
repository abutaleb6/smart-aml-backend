package com.smartaml.infrastructure.storage.hybrid;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class StorageSyncWorker {

    private final StorageSyncQueue queue;
    private static final Logger log = LoggerFactory.getLogger(StorageSyncWorker.class);

    public StorageSyncWorker(StorageSyncQueue queue) {
        this.queue = queue;
    }

    @Async("storageExecutor")
    public void processQueue() {
        while (true) {
            try {
                String path = queue.poll(5, TimeUnit.SECONDS);
                if (path == null) continue;
                // Here we'd attempt S3 upload and then delete local copy on success
                log.info("Syncing file to S3: {}", path);
                // Simulate success
                // Files.delete(localPath)
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            } catch (Exception ex) {
                log.error("Error during storage sync", ex);
            }
        }
    }
}
