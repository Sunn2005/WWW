package com.example.eshop.ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiChatConfig {

    private static final String SYSTEM_PROMPT = """
            Bạn là trợ lý tư vấn của eShop, cửa hàng đồ điện tử.
            Trả lời bằng tiếng Việt, ngắn gọn, thân thiện.
            Giá, tồn kho và trạng thái đơn hàng PHẢI lấy từ tool, không được bịa.
            Nếu hết hàng thì nói rõ là hết hàng, đừng gợi ý mua món đó nữa.
            Chỉ trả lời về sản phẩm, đơn hàng và chính sách của eShop.
            Chính sách demo: giao hàng 1-3 ngày, phí 30.000đ, miễn phí cho đơn từ 500.000đ;
            đổi trả trong 7 ngày nếu lỗi do nhà sản xuất; thanh toán COD hoặc chuyển khoản.
            Khi giới thiệu sản phẩm, luôn kèm link /products/{id}.
            """;

    @Bean
    public ChatMemory chatMemory() {
        return MessageWindowChatMemory.builder().maxMessages(20).build();
    }

    @Bean
    public ChatClient chatClient(ChatModel chatModel, ChatMemory chatMemory, EShopTools tools) {
        return ChatClient.builder(chatModel)
                .defaultSystem(SYSTEM_PROMPT)
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .defaultTools(tools)
                .build();
    }
}
