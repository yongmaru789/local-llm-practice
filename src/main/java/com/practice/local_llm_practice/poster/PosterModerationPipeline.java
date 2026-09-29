package com.practice.local_llm_practice.poster;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PosterModerationPipeline {

    private final PosterResolutionValidator posterResolutionValidator;
    private final PosterVisionReviewClient posterVisionReviewClient;

    public PosterModerationPipeline(
            PosterResolutionValidator posterResolutionValidator,
            PosterVisionReviewClient posterVisionReviewClient
    ) {
        this.posterResolutionValidator = posterResolutionValidator;
        this.posterVisionReviewClient = posterVisionReviewClient;
    }

    public PosterModerationResult review(byte[] imageBytes) throws IOException {
        List<String> violations = new ArrayList<>();
        violations.addAll(posterResolutionValidator.findResolutionViolations(imageBytes));
        violations.addAll(posterVisionReviewClient.review(imageBytes));

        if (!violations.isEmpty()) {
            return PosterModerationResult.reject(violations);
        }

        return PosterModerationResult.pass();
    }
}