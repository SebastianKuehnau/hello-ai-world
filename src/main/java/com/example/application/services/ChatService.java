package com.example.application.services;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class ChatService {

    public static final int MESSAGE_MEMORY_LIMIT = 50;
    private final ChatClient chatClient;

    public ChatService(ChatClient.Builder chatClientBuilder) {

        //for logging the communication with the chatbot
        var simpleLoggerAdvisor = new SimpleLoggerAdvisor();
        //for keeping track of the conversation history
        var chatMemory = MessageWindowChatMemory.builder().maxMessages(MESSAGE_MEMORY_LIMIT).build();
        //for creating a response based on the conversation history
        var chatMemoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();

        //create a chat client with the advisors
        chatClient = chatClientBuilder
                .defaultAdvisors(simpleLoggerAdvisor, chatMemoryAdvisor)
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
