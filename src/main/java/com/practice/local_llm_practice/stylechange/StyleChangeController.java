package com.practice.local_llm_practice.stylechange;

import java.io.IOException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StyleChangeController {

    private final StyleChangeService styleChangeService;

    public StyleChangeController(StyleChangeService styleChangeService) {
        this.styleChangeService = styleChangeService;
    }

    @GetMapping("/test/style-change")
    public StyleChangeResult testStyleChange(@RequestParam String message) throws IOException {
        return styleChangeService.extractBackgroundColor(message);
    }
}
