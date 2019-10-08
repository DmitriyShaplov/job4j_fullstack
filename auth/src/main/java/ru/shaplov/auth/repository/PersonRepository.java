package ru.shaplov.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shaplov.auth.domain.Person;

import java.util.Optional;

/**
 * @author shaplov
 * @since 30.09.2019
 */
public interface PersonRepository extends JpaRepository<Person, Integer> {
    Optional<Person> findByLogin(String login);
    void deleteByLogin(String login);
    Optional<Person> findByLoginAndPassword(String login, String password);
    Optional<Person> findByToken(String token);
}
