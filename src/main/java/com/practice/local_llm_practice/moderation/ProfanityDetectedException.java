package com.practice.local_llm_practice.moderation;

public class ProfanityDetectedException extends RuntimeException {

    public ProfanityDetectedException(String message) {
        super(message);
    }
}