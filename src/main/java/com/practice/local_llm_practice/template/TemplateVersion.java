package com.practice.local_llm_practice.template;

import java.time.LocalDateTime;

public class TemplateVersion {

    private final int versionNumber;
    private final String html;
    private final LocalDateTime createdAt;

    public TemplateVersion(int versionNumber, String html) {
        this.versionNumber = versionNumber;
        this.html = html;
        this.createdAt = LocalDateTime.now();
    }

    public int getVersionNumber() {
        return versionNumber;
    }

    public String getHtml() {
        return html;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
