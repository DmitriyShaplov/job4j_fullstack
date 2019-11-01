package ru.shaplov.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shaplov.auth.domain.Role;

import java.util.Optional;

/**
 * @author shaplov
 * @since 25.10.2019
 */
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String name);
}
