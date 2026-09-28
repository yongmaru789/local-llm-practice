package com.practice.local_llm_practice.template;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Service;

@Service
public class TemplateStore {

    private final Map<Long, Template> templates = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(0);

    public int count() {
        return templates.size();
    }

    public Template save(String html) {
        Long id = idGenerator.incrementAndGet();
        Template template = new Template(id, html);
        templates.put(id, template);
        return template;
    }

    public Template find(Long id) {
        return templates.get(id);
    }
}