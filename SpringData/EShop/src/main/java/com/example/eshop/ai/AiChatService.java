package com.example.eshop.ai;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AiChatService {

    private final ChatClient chatClient;

    public String chat(String conversationId, String message) {
        try {
            return chatClient.prompt()
                    .user(message)
                    .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, conversationId))
                    .call()
                    .content();
        } catch (Exception e) {
            return "Xin lỗi, trợ lý AI hiện không trả lời được. Bạn kiểm tra lại API key hoặc thử lại sau nhé.";
        }
    }
}
