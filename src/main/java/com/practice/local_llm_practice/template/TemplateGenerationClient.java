package com.practice.local_llm_practice.template;

import com.practice.local_llm_practice.ollama.OllamaMessage;
import com.practice.local_llm_practice.ollama.OllamaService;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

@Service
public class TemplateGenerationClient {

    private final OllamaService ollamaService;
    private final String templateGenerationSystemPrompt;

    public TemplateGenerationClient(OllamaService ollamaService) throws IOException {
        this.ollamaService = ollamaService;
        this.templateGenerationSystemPrompt = loadPrompt("prompts/template-generation-prompt.txt");
    }

    private String loadPrompt(String path) throws IOException {
        ClassPathResource resource = new ClassPathResource(path);
        return StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
    }

    public String generate(String adminRequest) {
        List<OllamaMessage> messages = List.of(
                new OllamaMessage("system", templateGenerationSystemPrompt),
                new OllamaMessage("user", adminRequest)
        );

        String rawContent = ollamaService.chat(messages);
        return stripCodeFence(rawContent);
    }

    private String stripCodeFence(String text) {
        return text.replace("```html", "").replace("```", "").trim();
    }
}
