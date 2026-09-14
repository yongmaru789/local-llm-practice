package com.practice.local_llm_practice;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class OllamaService {

    private final WebClient webClient;
    private final String styleChangeSystemPrompt;

    public OllamaService(WebClient.Builder webClientBuilder) throws IOException {
        this.webClient = webClientBuilder.baseUrl("http://localhost:11434").build();
        this.styleChangeSystemPrompt = loadPrompt("prompts/style-change-prompt.txt");
    }

    private String loadPrompt(String path) throws IOException {
        ClassPathResource resource = new ClassPathResource(path);
        return StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
    }

    public String chat(String userMessage) {
        OllamaChatRequest request = new OllamaChatRequest(
                "qwen3.5:2b",
                List.of(new OllamaMessage("user", userMessage)),
                false,
                false,
                "30m"
        );

        return callOllama(request);
    }

    public String extractBackgroundColor(String userMessage) {
        OllamaChatRequest request = new OllamaChatRequest(
                "qwen3.5:2b",
                List.of(
                        new OllamaMessage("system", styleChangeSystemPrompt),
                        new OllamaMessage("user", userMessage)
                ),
                false,
                false,
                "30m"
        );

        return callOllama(request);
    }

    private String callOllama(OllamaChatRequest request) {
        OllamaChatResponse response = webClient.post()
                .uri("/api/chat")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(OllamaChatResponse.class)
                .block();

        return response.message().content();
    }
}