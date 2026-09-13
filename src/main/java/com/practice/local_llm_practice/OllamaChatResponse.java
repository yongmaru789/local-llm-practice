package com.practice.local_llm_practice;

public record OllamaChatResponse(
        String model,
        OllamaMessage message,
        boolean done
) {
}
