package ru.shaplov.service;

import ru.shaplov.domain.Notification;

import java.util.List;

/**
 * @author shaplov
 * @since 17.09.2019
 */
public interface ReadMail {
    List<Notification> receiveMessage();
}
