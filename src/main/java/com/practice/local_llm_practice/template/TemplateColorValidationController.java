package com.practice.local_llm_practice.template;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TemplateColorValidationController {

    private final TemplateColorValidator templateColorValidator;

    public TemplateColorValidationController(TemplateColorValidator templateColorValidator) {
        this.templateColorValidator = templateColorValidator;
    }

    @PostMapping("/test/template-colors")
    public List<String> testDisallowedColors(@RequestBody String html) {
        return templateColorValidator.findDisallowedColors(html);
    }
}