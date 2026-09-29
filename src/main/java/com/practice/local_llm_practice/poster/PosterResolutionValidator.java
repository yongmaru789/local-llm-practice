package com.practice.local_llm_practice.poster;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import org.springframework.stereotype.Service;

@Service
public class PosterResolutionValidator {

    private static final int MIN_WIDTH = 800;
    private static final int MIN_HEIGHT = 600;

    public List<String> findResolutionViolations(byte[] imageBytes) throws IOException {
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes));
        List<String> violations = new ArrayList<>();

        if (image == null) {
            violations.add("이미지를 읽을 수 없습니다");
            return violations;
        }

        if (image.getWidth() < MIN_WIDTH || image.getHeight() < MIN_HEIGHT) {
            violations.add("해상도가 너무 낮습니다: " + image.getWidth() + "x" + image.getHeight());
        }

        return violations;
    }
}