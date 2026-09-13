package com.practice.local_llm_practice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private final OllamaService ollamaService;

    public TestController(OllamaService ollamaService) {
        this.ollamaService = ollamaService;
    }

    @GetMapping("/test/ollama")
    public String testOllama() {
        return ollamaService.chat("안녕");
    }

}