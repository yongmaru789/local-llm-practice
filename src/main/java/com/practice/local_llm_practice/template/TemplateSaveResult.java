package com.practice.local_llm_practice.template;

import java.util.List;

public record TemplateSaveResult(boolean success, Long templateId, int version, String html, List<String> errors) {

    public static TemplateSaveResult success(Long templateId, int version, String html) {
        return new TemplateSaveResult(true, templateId, version, html, List.of());
    }

    public static TemplateSaveResult failure(List<String> errors) {
        return new TemplateSaveResult(false, null, 0, null, errors);
    }
}