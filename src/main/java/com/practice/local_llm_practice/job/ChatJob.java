package com.practice.local_llm_practice.job;

import java.time.Duration;
import java.time.Instant;

public class ChatJob {

    private final String id;
    private final String message;
    private final Instant submittedAt;
    private volatile Instant startedAt;
    private volatile Instant completedAt;
    private volatile String result;
    private volatile boolean done;

    public ChatJob(String id, String message) {
        this.id = id;
        this.message = message;
        this.submittedAt = Instant.now();
        this.done = false;
    }

    public String getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public String getResult() {
        return result;
    }

    public boolean isDone() {
        return done;
    }

    public Instant getSubmittedAt() {
        return submittedAt;
    }

    public Instant getStartedAt() {
        return startedAt;
    }

    public Instant getCompletedAt() {
        return completedAt;
    }

    public Long getQueueWaitMillis() {
        if (startedAt == null) {
            return null;
        }
        return Duration.between(submittedAt, startedAt).toMillis();
    }

    public Long getProcessingMillis() {
        if (startedAt == null || completedAt == null) {
            return null;
        }
        return Duration.between(startedAt, completedAt).toMillis();
    }

    public void markStarted() {
        this.startedAt = Instant.now();
    }

    public void complete(String result) {
        this.completedAt = Instant.now();
        this.result = result;
        this.done = true;
    }
}