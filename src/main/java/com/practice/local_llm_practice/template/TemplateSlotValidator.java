package com.practice.local_llm_practice.template;

import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

@Service
public class TemplateSlotValidator {

    private static final List<String> REQUIRED_SLOTS = List.of(
            "eventName", "discount", "period", "image", "adLabel"
    );

    public List<String> findMissingSlots(String html) {
        Document document = Jsoup.parse(html);
        List<String> missing = new ArrayList<>();

        for (String slot : REQUIRED_SLOTS) {
            boolean exists = !document.select("[data-slot=" + slot + "]").isEmpty();
            if (!exists) {
                missing.add(slot);
            }
        }

        return missing;
    }
}