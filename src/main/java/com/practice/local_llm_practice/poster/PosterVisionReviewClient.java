package com.practice.local_llm_practice.poster;

import java.util.List;

public interface PosterVisionReviewClient {
    List<String> review(byte[] imageBytes);
}