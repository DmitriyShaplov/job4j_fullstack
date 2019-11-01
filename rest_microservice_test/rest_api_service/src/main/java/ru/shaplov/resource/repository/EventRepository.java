package ru.shaplov.resource.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shaplov.resource.domain.Event;

/**
 * @author shaplov
 * @since 31.10.2019
 */
public interface EventRepository extends JpaRepository<Event, Integer> {
}
