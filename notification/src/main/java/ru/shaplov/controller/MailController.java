package ru.shaplov.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import ru.shaplov.shop.domain.Notification;
import ru.shaplov.shop.service.ReadMail;

import java.util.List;

/**
 * @author shaplov
 * @since 17.09.2019
 */
@RestController
public class MailController {

    private final JavaMailSender sender;
    private final ReadMail reader;

    @Autowired
    public MailController(JavaMailSender sender, ReadMail reader) {
        this.sender = sender;
        this.reader = reader;
    }

    @GetMapping("/inbox")
    public List<Notification> receiveMail() {
        return reader.receiveMessage();
    }

    @PostMapping(value = "/send", consumes = "application/json")
    public ResponseEntity sendMail(@RequestBody Notification notification) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(notification.getTo());
        message.setSubject(notification.getSubject());
        message.setText(notification.getBody());
        sender.send(message);
        return new ResponseEntity(HttpStatus.OK);
    }
}
