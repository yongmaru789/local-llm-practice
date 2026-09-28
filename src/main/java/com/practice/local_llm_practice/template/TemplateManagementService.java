package com.practice.local_llm_practice.template;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TemplateManagementService {

    private static final int MAX_TEMPLATE_COUNT = 10;

    private final TemplateGenerationPipeline templateGenerationPipeline;
    private final TemplateStore templateStore;

    public TemplateManagementService(
            TemplateGenerationPipeline templateGenerationPipeline,
            TemplateStore templateStore
    ) {
        this.templateGenerationPipeline = templateGenerationPipeline;
        this.templateStore = templateStore;
    }

    public TemplateSaveResult createTemplate(String adminRequest) {
        if (templateStore.count() >= MAX_TEMPLATE_COUNT) {
            return TemplateSaveResult.failure(
                    List.of("템플릿 개수 제한을 초과했습니다. 최대 " + MAX_TEMPLATE_COUNT + "개까지 생성할 수 있습니다.")
            );
        }

        TemplateGenerationResult generationResult = templateGenerationPipeline.generate(adminRequest);
        if (!generationResult.success()) {
            return TemplateSaveResult.failure(generationResult.errors());
        }

        Template template = templateStore.save(generationResult.html());
        TemplateVersion latest = template.getLatestVersion();
        return TemplateSaveResult.success(template.getId(), latest.getVersionNumber(), latest.getHtml());
    }

    public TemplateSaveResult reviseTemplate(Long templateId, String adminRequest) {
        Template template = templateStore.find(templateId);
        if (template == null) {
            return TemplateSaveResult.failure(List.of("존재하지 않는 템플릿입니다: " + templateId));
        }

        TemplateGenerationResult generationResult = templateGenerationPipeline.generate(adminRequest);
        if (!generationResult.success()) {
            return TemplateSaveResult.failure(generationResult.errors());
        }

        TemplateVersion newVersion = template.addVersion(generationResult.html());
        return TemplateSaveResult.success(template.getId(), newVersion.getVersionNumber(), newVersion.getHtml());
    }
}