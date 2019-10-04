package ru.shaplov.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shaplov.auth.domain.Room;

import java.util.List;
import java.util.Optional;

/**
 * @author shaplov
 * @since 02.10.2019
 */
public interface RoomRepository extends JpaRepository<Room, Integer> {
    Optional<Room> findByName(String name);
    List<Room> findByPersonsLogin(String login);
}
