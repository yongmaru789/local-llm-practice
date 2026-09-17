package com.practice.local_llm_practice.job;

import com.practice.local_llm_practice.moderation.ProfanityDetectedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatJobController {

    private final ChatJobQueueService chatJobQueueService;

    public ChatJobController(ChatJobQueueService chatJobQueueService) {
        this.chatJobQueueService = chatJobQueueService;
    }

    @GetMapping("/test/chat-job")
    public ResponseEntity<String> submitJob(@RequestParam String message) {
        try {
            String id = chatJobQueueService.submit(message);
            return ResponseEntity.ok(id);
        } catch (ProfanityDetectedException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/test/chat-job/{id}")
    public ChatJob getJob(@PathVariable String id) {
        return chatJobQueueService.getJob(id);
    }
}