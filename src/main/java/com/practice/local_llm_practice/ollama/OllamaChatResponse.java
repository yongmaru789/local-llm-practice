package com.practice.local_llm_practice.ollama;

public record OllamaChatResponse(
        String model,
        OllamaMessage message,
        boolean done
) {
}
