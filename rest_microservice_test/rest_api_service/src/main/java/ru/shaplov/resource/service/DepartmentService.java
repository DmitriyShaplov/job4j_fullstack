package ru.shaplov.resource.service;

import ru.shaplov.resource.domain.Document;
import ru.shaplov.resource.domain.Request;
import ru.shaplov.resource.dto.RequestCriteria;

import java.util.List;

/**
 * @author shaplov
 * @since 01.11.2019
 */
public interface DepartmentService {
    List<Request> findByCriteria(RequestCriteria criteria);
    Request terminate(int id);
    Request getDetailedRequest(int id);
    Document downloadFile(int id);
}
