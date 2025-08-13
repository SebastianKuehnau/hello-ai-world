package com.example.application.views.chatbot;

import com.example.application.services.ChatService;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.messages.MessageInput;
import com.vaadin.flow.component.messages.MessageList;
import com.vaadin.flow.component.messages.MessageListItem;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

import java.time.Instant;

@PageTitle("Chat Bot")
@Route("")
@RouteAlias("chat-bot")
@Menu(order = 0, icon = LineAwesomeIconUrl.ROBOT_SOLID)
public class ChatBotView extends Composite<VerticalLayout> {

    private final ChatService chatService;
    private final MessageList messageList;
    private final Scroller scroller;

    public ChatBotView(ChatService chatService) {
        this.chatService = chatService;

        //Create a scrolling MessageList
        messageList = new MessageList();
        scroller = new Scroller(messageList);
        getContent().addAndExpand(scroller);

        //create a MessageInput and set a submit-listener
        var messageInput = new MessageInput();
        messageInput.addSubmitListener(this::onSubmit);
        messageInput.setWidthFull();

        getContent().add(messageInput);
    }

    private void onSubmit(MessageInput.SubmitEvent submitEvent) {
        //create and handle a prompt message
        var promptMessage = new MessageListItem(submitEvent.getValue(),
                Instant.now(), "User");
        promptMessage.setUserColorIndex(0);
        messageList.addItem(promptMessage);

        //create amd handle response message
        var responseMessage = new MessageListItem("",
                Instant.now(), "Bot");
        responseMessage.setUserColorIndex(1);
        messageList.addItem(responseMessage);

        //append a response message to the existing UI
        var uiOptional = submitEvent.getSource().getUI();
        chatService.chatStream(submitEvent.getValue()).subscribe(token ->
                uiOptional.ifPresent(ui -> ui.access(() -> {
                    responseMessage.appendText(token);
                    scroller.scrollToBottom();
                }))
        );
    }
}
