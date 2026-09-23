package com.practice.local_llm_practice.template;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TemplateSlotValidationController {

    private final TemplateSlotValidator templateSlotValidator;

    public TemplateSlotValidationController(TemplateSlotValidator templateSlotValidator) {
        this.templateSlotValidator = templateSlotValidator;
    }

    @PostMapping("/test/template-slots")
    public List<String> testMissingSlots(@RequestBody String html) {
        return templateSlotValidator.findMissingSlots(html);
    }
}