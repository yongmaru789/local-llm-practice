package com.practice.local_llm_practice.template;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TemplateGenerationController {

    private final TemplateGenerationPipeline templateGenerationPipeline;
    private final TemplateGenerationClient templateGenerationClient;

    public TemplateGenerationController(
            TemplateGenerationPipeline templateGenerationPipeline,
            TemplateGenerationClient templateGenerationClient
    ) {
        this.templateGenerationPipeline = templateGenerationPipeline;
        this.templateGenerationClient = templateGenerationClient;
    }

    @GetMapping("/test/template-generate")
    public TemplateGenerationResult testGenerate(@RequestParam String request) {
        return templateGenerationPipeline.generate(request);
    }

    @GetMapping("/test/template-raw")
    public String testRawGenerate(@RequestParam String request) {
        return templateGenerationClient.generate(request);
    }
}
