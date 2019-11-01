package ru.shaplov.auth.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.shaplov.auth.domain.SimpleUser;

import java.util.Optional;

/**
 * @author shaplov
 * @since 25.10.2019
 */
public interface UserRepository extends JpaRepository<SimpleUser, Integer> {
    @EntityGraph(attributePaths = {"roles"})
    Optional<SimpleUser> findByUsername(String username);
}
