package com.practice.local_llm_practice.poster;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PosterModerationController {

    private final PosterModerationPipeline posterModerationPipeline;

    public PosterModerationController(PosterModerationPipeline posterModerationPipeline) {
        this.posterModerationPipeline = posterModerationPipeline;
    }

    @GetMapping("/test/poster-review")
    public PosterModerationResult testReview(@RequestParam String filePath) throws IOException {
        byte[] imageBytes = Files.readAllBytes(Path.of(filePath));
        return posterModerationPipeline.review(imageBytes);
    }
}