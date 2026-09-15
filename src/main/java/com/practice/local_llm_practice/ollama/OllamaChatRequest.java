package com.practice.local_llm_practice.ollama;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record OllamaChatRequest(
        String model,
        List<OllamaMessage> messages,
        boolean stream,
        boolean think,
        @JsonProperty("keep_alive") String keepAlive
) {
}