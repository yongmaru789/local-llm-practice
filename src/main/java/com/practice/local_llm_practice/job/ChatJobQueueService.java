package com.practice.local_llm_practice.job;

import com.practice.local_llm_practice.ollama.OllamaService;
import jakarta.annotation.PostConstruct;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import org.springframework.stereotype.Service;

@Service
public class ChatJobQueueService {

    private final BlockingQueue<ChatJob> queue = new LinkedBlockingQueue<>();
    private final Map<String, ChatJob> jobs = new ConcurrentHashMap<>();
    private final OllamaService ollamaService;

    public ChatJobQueueService(OllamaService ollamaService) {
        this.ollamaService = ollamaService;
    }

    @PostConstruct
    public void startWorker() {
        Thread worker = new Thread(this::processQueue);
        worker.setDaemon(true);
        worker.start();
    }

    public String submit(String message) {
        String id = UUID.randomUUID().toString();
        ChatJob job = new ChatJob(id, message);
        jobs.put(id, job);
        queue.add(job);
        return id;
    }

    public ChatJob getJob(String id) {
        return jobs.get(id);
    }

    private void processQueue() {
        while (true) {
            try {
                ChatJob job = queue.take();
                job.markStarted();
                String result = ollamaService.chat(job.getMessage());
                job.complete(result);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}