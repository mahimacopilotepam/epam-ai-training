package org.epam.ai.config;

import dev.langchain4j.model.azure.AzureOpenAiChatModel;
import dev.langchain4j.model.chat.ChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAIConfig {

    @Value("${langchain4j.azure-open-ai.api-key}")
    private String apiKey;

    @Value("${langchain4j.azure-open-ai.endpoint}")
    private String apiUrl;

    @Value("${langchain4j.azure-open-ai.deployment-name}")
    private String deploymentName;

    @Bean
    public ChatModel chatModel() {
        return AzureOpenAiChatModel.builder()
                .apiKey(apiKey)
                .endpoint(apiUrl)
                .deploymentName(deploymentName)
                .build();
    }
}