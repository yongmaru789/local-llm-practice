package com.practice.local_llm_practice;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class OllamaService {
    private final WebClient webClient;

    public OllamaService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:11434").build();
    }

    public String chat(String userMessage) {
        OllamaChatRequest request = new OllamaChatRequest(
                "qwen3.5:2b",
                List.of(new OllamaMessage("user", userMessage)),
                false,
                false,
                "30m"
        );

        OllamaChatResponse response = webClient.post()
                .uri("/api/chat")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(OllamaChatResponse.class)
                .block();

        return response.message().content();
    }
}