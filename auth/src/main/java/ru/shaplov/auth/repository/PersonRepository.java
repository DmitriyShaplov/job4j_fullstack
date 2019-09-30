package ru.shaplov.auth.repository;

import org.springframework.data.repository.CrudRepository;
import ru.shaplov.auth.domain.Person;

/**
 * @author shaplov
 * @since 30.09.2019
 */
public interface PersonRepository extends CrudRepository<Person, Integer> {
}
