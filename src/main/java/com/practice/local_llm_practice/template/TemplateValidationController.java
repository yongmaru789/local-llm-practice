package com.practice.local_llm_practice.template;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TemplateValidationController {

    private final TemplateHtmlValidator templateHtmlValidator;

    public TemplateValidationController(TemplateHtmlValidator templateHtmlValidator) {
        this.templateHtmlValidator = templateHtmlValidator;
    }

    @PostMapping("/test/template-sanitize")
    public String testSanitize(@RequestBody String html) {
        return templateHtmlValidator.sanitize(html);
    }
}