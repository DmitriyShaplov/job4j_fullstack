package ru.shaplov.resource.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.shaplov.resource.domain.Document;

import java.util.Optional;

/**
 * @author shaplov
 * @since 31.10.2019
 */
public interface DocumentRepository extends JpaRepository<Document, Integer> {

    @EntityGraph(attributePaths = {"data"})
    Optional<Document> getById(Integer id);
}
