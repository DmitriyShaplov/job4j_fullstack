package ru.shaplov.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shaplov.auth.domain.Message;

import java.util.List;

/**
 * @author shaplov
 * @since 02.10.2019
 */
public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message> findAllByRoomNameOrderByIdDesc(String roomName);
    List<Message> findAllByPersonLoginOrderByIdDesc(String personLogin);
    void deleteAllByRoomName(String roomName);
    void deleteAllByPersonLogin(String login);
}
