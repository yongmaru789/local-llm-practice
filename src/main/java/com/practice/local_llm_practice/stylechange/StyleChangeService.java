package com.practice.local_llm_practice.stylechange;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.local_llm_practice.ollama.OllamaMessage;
import com.practice.local_llm_practice.ollama.OllamaService;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

@Service
public class StyleChangeService {

    private final OllamaService ollamaService;
    private final String styleChangeSystemPrompt;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public StyleChangeService(OllamaService ollamaService) throws IOException {
        this.ollamaService = ollamaService;
        this.styleChangeSystemPrompt = loadPrompt("prompts/style-change-prompt.txt");
    }

    private String loadPrompt(String path) throws IOException {
        ClassPathResource resource = new ClassPathResource(path);
        return StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
    }

    public StyleChangeResult extractBackgroundColor(String userMessage) throws IOException {
        List<OllamaMessage> messages = List.of(
                new OllamaMessage("system", styleChangeSystemPrompt),
                new OllamaMessage("user", userMessage)
        );

        String rawContent = ollamaService.chat(messages);
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
}
