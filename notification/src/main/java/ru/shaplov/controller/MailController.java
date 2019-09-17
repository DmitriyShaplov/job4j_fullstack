package ru.shaplov.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.shaplov.domain.Notification;
import ru.shaplov.service.MailService;

import java.util.List;

/**
 * @author shaplov
 * @since 17.09.2019
 */
@RestController
public class MailController {
    private final MailService service;

    @Autowired
    public MailController(MailService service) {
        this.service = service;
    }

    @GetMapping("/inbox")
    public List<Notification> receiveMail() {
        return service.receiveMessage();
    }

    @PostMapping(value = "/send", consumes = "application/json")
    public void sendMail(@RequestBody Notification notification) {
        service.sendMessage(notification);
    }
}
