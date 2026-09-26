package com.practice.local_llm_practice.template;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TemplateGenerationController {

    private final TemplateGenerationPipeline templateGenerationPipeline;

    public TemplateGenerationController(TemplateGenerationPipeline templateGenerationPipeline) {
        this.templateGenerationPipeline = templateGenerationPipeline;
    }

    @GetMapping("/test/template-generate")
    public TemplateGenerationResult testGenerate(@RequestParam String request) {
        return templateGenerationPipeline.generate(request);
    }
}