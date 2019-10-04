package ru.shaplov.auth.service;

import ru.shaplov.auth.domain.Message;
import ru.shaplov.auth.domain.Person;
import ru.shaplov.auth.domain.Room;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

/**
 * @author shaplov
 * @since 02.10.2019
 */
public interface RoomService {
    Room create(Room room);
    void delete(String roomName);
    Set<Person> enter(Person person, String roomName);
    Set<Person> leave(Person person, String roomName);
    Set<Person> persons(HttpServletRequest request, String roomName);
    Message post(Message message, String roomName);
    void delete(HttpServletRequest request, int messageId);
    List<Message> list(HttpServletRequest request, String roomName);
}
