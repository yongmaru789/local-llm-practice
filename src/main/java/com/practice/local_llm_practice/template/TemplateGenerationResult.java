package com.practice.local_llm_practice.template;

import java.util.List;

public record TemplateGenerationResult(boolean success, String html, List<String> errors) {

    public static TemplateGenerationResult success(String html) {
        return new TemplateGenerationResult(true, html, List.of());
    }

    public static TemplateGenerationResult failure(List<String> errors) {
        return new TemplateGenerationResult(false, null, errors);
    }
}