package com.practice.local_llm_practice.template;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class TemplateGenerationPipelineTest {

    @Test
    void 필수_슬롯과_허용된_색상만_있으면_생성에_성공한다() {
        String html = """
                <div data-slot="eventName">가을 세일</div>
                <div data-slot="discount">20% 할인</div>
                <div data-slot="period">10월 한 달간</div>
                <div data-slot="image" style="background-color: #F7C548;"></div>
                <div data-slot="adLabel">광고</div>
                """;
        TemplateGenerationPipeline pipeline = pipelineReturning(html);

        TemplateGenerationResult result = pipeline.generate("가을 세일 포스터 만들어줘");

        assertTrue(result.success());
        assertTrue(result.errors().isEmpty());
    }

    @Test
    void 필수_슬롯이_하나라도_빠지면_거부되고_사유에_슬롯_이름이_담긴다() {
        String html = """
                <div data-slot="eventName">가을 세일</div>
                <div data-slot="discount">20% 할인</div>
                <div data-slot="period">10월 한 달간</div>
                <div data-slot="adLabel">광고</div>
                """;
        TemplateGenerationPipeline pipeline = pipelineReturning(html);

        TemplateGenerationResult result = pipeline.generate("가을 세일 포스터 만들어줘");

        assertFalse(result.success());
        assertNull(result.html());
        assertTrue(result.errors().contains("필수 슬롯 누락: image"));
    }

    @Test
    void 팔레트_밖_색상이_있으면_거부되고_사유에_그_색상이_담긴다() {
        String html = """
                <div data-slot="eventName">가을 세일</div>
                <div data-slot="discount">20% 할인</div>
                <div data-slot="period">10월 한 달간</div>
                <div data-slot="image" style="background-color: red;"></div>
                <div data-slot="adLabel">광고</div>
                """;
        TemplateGenerationPipeline pipeline = pipelineReturning(html);

        TemplateGenerationResult result = pipeline.generate("가을 세일 포스터 만들어줘");

        assertFalse(result.success());
        assertTrue(result.errors().contains("허용되지 않은 색상: red"));
    }

    @Test
    void script_태그는_정화되어_최종_결과에_남지_않는다() {
        String html = """
                <div data-slot="eventName">가을 세일</div>
                <div data-slot="discount">20% 할인</div>
                <div data-slot="period">10월 한 달간</div>
                <div data-slot="image" style="background-color: #F7C548;"></div>
                <div data-slot="adLabel">광고</div>
                <script>alert('xss')</script>
                """;
        TemplateGenerationPipeline pipeline = pipelineReturning(html);

        TemplateGenerationResult result = pipeline.generate("가을 세일 포스터 만들어줘");

        assertTrue(result.success());
        assertFalse(result.html().contains("script"));
    }

    @Test
    void 슬롯_누락과_색상_위반이_동시에_있으면_사유가_모두_담긴다() {
        String html = """
                <div data-slot="eventName">가을 세일</div>
                <div data-slot="discount">20% 할인</div>
                <div data-slot="image" style="background-color: red;"></div>
                <div data-slot="adLabel">광고</div>
                """;
        TemplateGenerationPipeline pipeline = pipelineReturning(html);

        TemplateGenerationResult result = pipeline.generate("가을 세일 포스터 만들어줘");

        assertFalse(result.success());
        assertEquals(2, result.errors().size());
        assertTrue(result.errors().contains("필수 슬롯 누락: period"));
        assertTrue(result.errors().contains("허용되지 않은 색상: red"));
    }

    private TemplateGenerationPipeline pipelineReturning(String html) {
        TemplateLlmClient fixedResponseClient = adminRequest -> html;
        return new TemplateGenerationPipeline(
                fixedResponseClient,
                new TemplateHtmlValidator(),
                new TemplateSlotValidator(),
                new TemplateColorValidator()
        );
    }
}