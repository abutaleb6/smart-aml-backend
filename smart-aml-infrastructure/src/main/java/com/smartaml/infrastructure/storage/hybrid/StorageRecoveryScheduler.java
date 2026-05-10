package com.smartaml.infrastructure.storage.hybrid;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class StorageRecoveryScheduler {
    private final StorageSyncQueue queue;
    private final String localPath = "./uploads"; // should be injected from props
    private static final Logger log = LoggerFactory.getLogger(StorageRecoveryScheduler.class);

    public StorageRecoveryScheduler(StorageSyncQueue queue) { this.queue = queue; }

    @Scheduled(fixedDelay = 300_000)
    public void scanAndRequeue() {
        try (Stream<Path> paths = Files.walk(Path.of(localPath))) {
            paths.filter(Files::isRegularFile).forEach(p -> {
                try {
                    if (Files.getLastModifiedTime(p).toInstant().isBefore(Instant.now().minusSeconds(120))) {
                        String rel = localPath.relativize(p).toString();
                        queue.enqueue(rel);
                    }
                } catch (Exception ignored) {}
            });
        } catch (Exception ex) {
            log.error("Error scanning local storage for recovery", ex);
        }
    }
}
