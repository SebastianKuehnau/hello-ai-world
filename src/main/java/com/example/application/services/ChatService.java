package com.example.application.services;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class ChatService {

    private final ChatClient chatClient;

    public ChatService(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory) {

        //for creating a response based on the conversation history
        var chatMemoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();

        //create a chat client with the advisors
        chatClient = chatClientBuilder
                .defaultAdvisors(chatMemoryAdvisor)
                .build();
    }

    public Flux<String> chatStream(String userInput) {
        //send the user input to the chatbot and return responses as a stream
        return chatClient.prompt()
                .user(userInput)
                .stream()
                .content();
    }
}
