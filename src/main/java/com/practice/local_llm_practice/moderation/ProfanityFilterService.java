package com.practice.local_llm_practice.moderation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service
public class ProfanityFilterService {

    private final Set<String> bannedWords = new HashSet<>();

    public ProfanityFilterService() throws IOException {
        ClassPathResource resource = new ClassPathResource("badwords-ko.txt");
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.isBlank()) {
                    bannedWords.add(line.trim());
                }
            }
        }
    }

    public boolean containsProfanity(String text) {
        for (String word : bannedWords) {
            if (text.contains(word)) {
                return true;
            }
        }
        return false;
    }
}