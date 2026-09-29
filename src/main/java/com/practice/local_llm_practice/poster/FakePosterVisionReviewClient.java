package com.practice.local_llm_practice.poster;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class FakePosterVisionReviewClient implements PosterVisionReviewClient {

    @Override
    public List<String> review(byte[] imageBytes) {
        return List.of();
    }
}