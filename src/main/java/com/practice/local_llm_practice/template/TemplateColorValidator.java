package com.practice.local_llm_practice.template;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

@Service
public class TemplateColorValidator {

    private static final Set<String> ALLOWED_COLORS = Set.of(
            "#ffffff", "#000000", "#ff6b35", "#f7c548", "#2ec4b6", "#e63946", "#1d3557"
    );

    private static final Pattern COLOR_PROPERTY_PATTERN =
            Pattern.compile("(?:color|background-color|background)\\s*:\\s*([^;]+)", Pattern.CASE_INSENSITIVE);

    public List<String> findDisallowedColors(String html) {
        Document document = Jsoup.parse(html);
        List<String> disallowed = new ArrayList<>();

        for (Element element : document.select("[style]")) {
            String style = element.attr("style");
            Matcher matcher = COLOR_PROPERTY_PATTERN.matcher(style);

            while (matcher.find()) {
                String value = matcher.group(1).trim().toLowerCase();
                if (!ALLOWED_COLORS.contains(value)) {
                    disallowed.add(value);
                }
            }
        }

        return disallowed;
    }
}