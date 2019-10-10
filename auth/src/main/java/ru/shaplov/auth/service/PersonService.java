package ru.shaplov.auth.service;

import ru.shaplov.auth.domain.Person;

import java.util.List;
import java.util.Optional;

/**
 * @author shaplov
 * @since 02.10.2019
 */
public interface PersonService {
    List<Person> findAll();
    Optional<Person> findByLogin(String login);
    Person save(Person person);
    void delete(String login);
}
