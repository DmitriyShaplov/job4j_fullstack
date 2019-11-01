package ru.shaplov.resource.repository;

import ru.shaplov.resource.domain.Request;
import ru.shaplov.resource.dto.RequestCriteria;

import java.util.List;

/**
 * @author shaplov
 * @since 01.11.2019
 */
public interface CustomRequestRepository {
    List<Request> findAllSorted(RequestCriteria criteria);
}
