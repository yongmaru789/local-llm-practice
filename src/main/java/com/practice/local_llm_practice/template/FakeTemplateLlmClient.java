package com.practice.local_llm_practice.template;

import org.springframework.stereotype.Service;

@Service
public class FakeTemplateLlmClient implements TemplateLlmClient {

    private static final String FIXED_HTML = """
            <div data-slot="eventName">가짜 이벤트</div>
            <div data-slot="discount">가짜 할인 30%</div>
            <div data-slot="period">2026.01.01 ~ 2026.01.31</div>
            <div data-slot="image" style="background-color: #F7C548;"></div>
            <div data-slot="adLabel">광고</div>
            """;

    @Override
    public String generate(String adminRequest) {
        return FIXED_HTML;
    }
}