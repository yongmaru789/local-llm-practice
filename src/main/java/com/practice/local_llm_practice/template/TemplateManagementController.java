package com.practice.local_llm_practice.template;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TemplateManagementController {

    private final TemplateManagementService templateManagementService;

    public TemplateManagementController(TemplateManagementService templateManagementService) {
        this.templateManagementService = templateManagementService;
    }

    @GetMapping("/test/template-create")
    public TemplateSaveResult testCreate(@RequestParam String request) {
        return templateManagementService.createTemplate(request);
    }

    @GetMapping("/test/template-revise")
    public TemplateSaveResult testRevise(@RequestParam Long templateId, @RequestParam String request) {
        return templateManagementService.reviseTemplate(templateId, request);
    }
}