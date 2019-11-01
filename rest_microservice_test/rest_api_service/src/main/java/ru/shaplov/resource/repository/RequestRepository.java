package ru.shaplov.resource.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.shaplov.resource.domain.Request;

import java.util.Optional;

/**
 * @author shaplov
 * @since 31.10.2019
 */
public interface RequestRepository extends JpaRepository<Request, Integer>, JpaSpecificationExecutor<Request>, CustomRequestRepository {

    @EntityGraph(attributePaths = {"documents", "events"}, type = EntityGraph.EntityGraphType.FETCH)
    Optional<Request> getById(Integer id);
}
