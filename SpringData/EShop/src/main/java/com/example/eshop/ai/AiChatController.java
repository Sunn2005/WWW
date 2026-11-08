package com.example.eshop.ai;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AiChatController {

    private final AiChatService aiChatService;

    public record AiChatRequest(String message, String conversationId) {}
    public record AiChatResponse(String reply, String conversationId) {}

    @PostMapping("/chat")
    public AiChatResponse chat(@RequestBody AiChatRequest req) {
        String conversationId = req.conversationId() != null && !req.conversationId().isBlank()
                ? req.conversationId() : UUID.randomUUID().toString();
        String reply = aiChatService.chat(conversationId, req.message());
        return new AiChatResponse(reply, conversationId);
    }
}
