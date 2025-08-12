package com.example.application.views.chatbot;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

@PageTitle("Chat Bot")
@Route("")
@Menu(order = 0, icon = LineAwesomeIconUrl.ROBOT_SOLID)
public class ChatBotView extends Composite<VerticalLayout> {

    public ChatBotView() {
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
    }
}
