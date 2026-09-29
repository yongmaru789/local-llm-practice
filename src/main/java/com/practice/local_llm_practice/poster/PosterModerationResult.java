package com.practice.local_llm_practice.poster;

import java.util.List;

public record PosterModerationResult(boolean passed, List<String> violations) {

    public static PosterModerationResult pass() {
        return new PosterModerationResult(true, List.of());
    }

    public static PosterModerationResult reject(List<String> violations) {
        return new PosterModerationResult(false, violations);
    }
}