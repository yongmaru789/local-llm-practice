package com.practice.local_llm_practice.moderation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.HashSet;
import java.util.Set;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service
public class ProfanityFilterService {

    private static final String BOM = "\uFEFF";

    private final Set<String> bannedWords = new HashSet<>();

    public ProfanityFilterService() throws IOException {
        ClassPathResource resource = new ClassPathResource("badwords-ko.txt");
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String cleaned = line.replace(BOM, "").trim();
                if (!cleaned.isBlank()) {
                    bannedWords.add(normalize(cleaned));
                }
            }
        }
    }

    public boolean containsProfanity(String text) {
        String normalizedText = normalize(text);
        for (String word : bannedWords) {
            if (normalizedText.contains(word)) {
                return true;
            }
        }
        return false;
    }

    private String normalize(String text) {
        return Normalizer.normalize(text, Normalizer.Form.NFC);
    }
}