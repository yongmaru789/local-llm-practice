package com.practice.local_llm_practice.ollama;

import com.fasterxml.jackson.databind.ObjectMapper;
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
    private final ObjectMapper objectMapper = new ObjectMapper();

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

    public StyleChangeResult extractBackgroundColor(String userMessage) throws IOException {
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

        String rawContent = callOllama(request);
        String cleaned = stripCodeFence(rawContent);
        StyleChangeResult result = objectMapper.readValue(cleaned, StyleChangeResult.class);

        return normalize(result);
    }

    private String stripCodeFence(String text) {
        return text.replace("```json", "").replace("```", "").trim();
    }

    private StyleChangeResult normalize(StyleChangeResult result) {
        String color = result.backgroundColor();
        if (!color.startsWith("#")) {
            color = "#" + color;
        }
        return new StyleChangeResult(color);
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