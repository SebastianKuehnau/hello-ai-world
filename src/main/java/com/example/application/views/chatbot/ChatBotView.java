package com.example.application.views.chatbot;

import com.example.application.services.ChatService;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.messages.MessageInput;
import com.vaadin.flow.component.messages.MessageList;
import com.vaadin.flow.component.messages.MessageListItem;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

import java.time.Instant;
import java.util.UUID;

@PageTitle("Chat Bot")
@Route("")
@RouteAlias("chat-bot")
@Menu(order = 0, icon = LineAwesomeIconUrl.ROBOT_SOLID)
public class ChatBotView extends Composite<VerticalLayout> {

    private final ChatService chatService;
    private final MessageList messageList;

    //to identify the chat session and keep the context secured
    private final String chatId = UUID.randomUUID().toString();

    public ChatBotView(ChatService chatService) {
        this.chatService = chatService;

        //Create a scrolling MessageList
        messageList = new MessageList();
        messageList.setWidthFull();
        messageList.addClassName("streaming");
        var scroller = new Scroller(messageList);
        getContent().addAndExpand(scroller);

        //create a MessageInput and set a submit-listener
        var messageInput = new MessageInput();
        messageInput.addSubmitListener(this::onSubmit);
        messageInput.setWidthFull();

        getContent().add(messageInput);
        getContent().addClassName("chat");
    }

    private void onSubmit(MessageInput.SubmitEvent submitEvent) {
        //create and handle a prompt message
        var promptMessage = new MessageListItem(submitEvent.getValue(), Instant.now(), "User");
        promptMessage.setUserColorIndex(0);
        messageList.addItem(promptMessage);

        //create and handle the response message
        var responseMessage = new MessageListItem("", Instant.now(), "Bot");
        responseMessage.setUserColorIndex(1);
        messageList.addItem(responseMessage);

        //append a response message to the existing UI
        var userPrompt = submitEvent.getValue();
        var uiOptional = submitEvent.getSource().getUI();
        var ui = uiOptional.orElse(null); //implementation via ifPresent also possible

        if (ui != null) {
            chatService.chatStream(userPrompt, chatId)
                    .subscribe(token ->
                                    ui.access(() -> {
                                                addClassName("streaming");
                                                responseMessage.appendText(token);
                                                //removed because of scrolling issues while streaming
                                                //scroller.scrollToBottom();
                                                //add css for autoscroll
                                            }
                                    ),
                            throwable -> Notification
                                    .show("Error: " + throwable.getMessage())
                                    .addThemeVariants(NotificationVariant.LUMO_ERROR),
                            () -> ui.access(() -> removeClassName("streaming")));
        }
    }
}
