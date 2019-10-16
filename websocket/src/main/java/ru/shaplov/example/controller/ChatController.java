package ru.shaplov.example.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import ru.shaplov.example.domain.Msg;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author shaplov
 * @since 16.10.2019
 */
@Controller
public class ChatController {

    private final List<Msg> messages = new CopyOnWriteArrayList<>();

    @MessageMapping("/chat/{id}/save")
    @SendTo("/chat/{id}/save")
    public Msg save(@DestinationVariable int id, Msg message) {
        messages.add(message);
        return message;
    }
}
