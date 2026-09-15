package com.practice.local_llm_practice.ollama;

import java.io.IOException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private final OllamaService ollamaService;

    public TestController(OllamaService ollamaService) {
        this.ollamaService = ollamaService;
    }

    @GetMapping("/test/ollama")
    public String testOllama(@RequestParam String message) {
        return ollamaService.chat(message);
    }

    @GetMapping("/test/style-change")
    public StyleChangeResult testStyleChange(@RequestParam String message) throws IOException {
        return ollamaService.extractBackgroundColor(message);
    }
}