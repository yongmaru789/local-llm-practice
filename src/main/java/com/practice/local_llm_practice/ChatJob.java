package com.practice.local_llm_practice;

public class ChatJob {

    private final String id;
    private final String message;
    private volatile String result;
    private volatile boolean done;

    public ChatJob(String id, String message) {
        this.id = id;
        this.message = message;
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

    public void complete(String result) {
        this.result = result;
        this.done = true;
    }
}