package com.smartaml.infrastructure.storage.hybrid;

import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@Component
public class StorageSyncQueue {
    private final LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>(1000);

    public void enqueue(String relativePath) {
        queue.offer(relativePath);
    }

    public String poll(long timeout, TimeUnit unit) throws InterruptedException {
        return queue.poll(timeout, unit);
    }
}
