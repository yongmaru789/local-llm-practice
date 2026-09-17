package com.practice.local_llm_practice.moderation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfanityTestController {

    private final ProfanityFilterService profanityFilterService;

    public ProfanityTestController(ProfanityFilterService profanityFilterService) {
        this.profanityFilterService = profanityFilterService;
    }

    @GetMapping("/test/profanity")
    public String testProfanity(@RequestParam String text) {
        long start = System.nanoTime();
        boolean flagged = profanityFilterService.containsProfanity(text);
        long elapsedMicros = (System.nanoTime() - start) / 1000;
        return "flagged=" + flagged + ", elapsedMicros=" + elapsedMicros;
    }
}