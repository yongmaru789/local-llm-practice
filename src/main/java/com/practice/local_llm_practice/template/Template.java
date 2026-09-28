package com.practice.local_llm_practice.template;

import java.util.ArrayList;
import java.util.List;

public class Template {

    private final Long id;
    private final List<TemplateVersion> versions = new ArrayList<>();

    public Template(Long id, String html) {
        this.id = id;
        this.versions.add(new TemplateVersion(1, html));
    }

    public Long getId() {
        return id;
    }

    public TemplateVersion addVersion(String html) {
        TemplateVersion version = new TemplateVersion(versions.size() + 1, html);
        versions.add(version);
        return version;
    }

    public TemplateVersion getLatestVersion() {
        return versions.get(versions.size() - 1);
    }

    public List<TemplateVersion> getVersions() {
        return versions;
    }
}