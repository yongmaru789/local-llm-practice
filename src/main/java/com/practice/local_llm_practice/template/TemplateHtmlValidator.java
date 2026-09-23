package com.practice.local_llm_practice.template;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.stereotype.Service;

@Service
public class TemplateHtmlValidator {

    private static final Safelist SAFELIST = buildSafelist();

    public String sanitize(String html) {
        return Jsoup.clean(html, SAFELIST);
    }

    private static Safelist buildSafelist() {
        Safelist safelist = Safelist.none();

        String[] allowedTags = {"div", "h1", "h2", "h3", "p", "span", "img"};
        for (String tag : allowedTags) {
            safelist.addTags(tag);
            safelist.addAttributes(tag, "style", "class", "data-slot");
        }

        safelist.addAttributes("img", "src", "alt", "width", "height");
        safelist.addProtocols("img", "src", "http", "https");

        return safelist;
    }
}