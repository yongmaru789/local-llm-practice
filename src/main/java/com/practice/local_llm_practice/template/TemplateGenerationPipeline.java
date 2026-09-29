package com.practice.local_llm_practice.template;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TemplateGenerationPipeline {

    private final TemplateLlmClient templateLlmClient;
    private final TemplateHtmlValidator templateHtmlValidator;
    private final TemplateSlotValidator templateSlotValidator;
    private final TemplateColorValidator templateColorValidator;

    public TemplateGenerationPipeline(
            TemplateLlmClient templateLlmClient,
            TemplateHtmlValidator templateHtmlValidator,
            TemplateSlotValidator templateSlotValidator,
            TemplateColorValidator templateColorValidator
    ) {
        this.templateLlmClient = templateLlmClient;
        this.templateHtmlValidator = templateHtmlValidator;
        this.templateSlotValidator = templateSlotValidator;
        this.templateColorValidator = templateColorValidator;
    }

    public TemplateGenerationResult generate(String adminRequest) {
        String rawHtml = templateLlmClient.generate(adminRequest);
        String cleanHtml = templateHtmlValidator.sanitize(rawHtml);

        List<String> errors = new ArrayList<>();

        for (String slot : templateSlotValidator.findMissingSlots(cleanHtml)) {
            errors.add("필수 슬롯 누락: " + slot);
        }

        for (String color : templateColorValidator.findDisallowedColors(cleanHtml)) {
            errors.add("허용되지 않은 색상: " + color);
        }

        if (!errors.isEmpty()) {
            return TemplateGenerationResult.failure(errors);
        }

        return TemplateGenerationResult.success(cleanHtml);
    }
}