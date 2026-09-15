package com.practice.local_llm_practice;

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
    public String submitJob(@RequestParam String message) {
        return chatJobQueueService.submit(message);
    }

    @GetMapping("/test/chat-job/{id}")
    public ChatJob getJob(@PathVariable String id) {
        return chatJobQueueService.getJob(id);
    }
}