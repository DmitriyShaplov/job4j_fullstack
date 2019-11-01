package ru.shaplov.resource.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shaplov.resource.domain.Passport;

import java.util.Optional;

/**
 * @author shaplov
 * @since 31.10.2019
 */
public interface PassportRepository extends JpaRepository<Passport, Integer> {
    Optional<Passport> findByNumberAndSeries(String number, String series);
}
