package ru.shaplov.resource.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shaplov.resource.domain.Document;
import ru.shaplov.resource.domain.Event;
import ru.shaplov.resource.domain.Request;
import ru.shaplov.resource.domain.Status;
import ru.shaplov.resource.dto.RequestCriteria;
import ru.shaplov.resource.repository.DocumentRepository;
import ru.shaplov.resource.repository.RequestRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author shaplov
 * @since 01.11.2019
 */
@Service
@Transactional(readOnly = true)
public class DepartmentServiceImpl implements DepartmentService {

    private final RequestRepository requestRepository;
    private final DocumentRepository documentRepository;

    @Autowired
    public DepartmentServiceImpl(RequestRepository requestRepository, DocumentRepository documentRepository) {
        this.requestRepository = requestRepository;
        this.documentRepository = documentRepository;
    }

    @Override
    @Transactional
    public Request terminate(int id) {
        Request request = requestRepository.getById(id).orElseThrow();
        if (!request.getStatus().equals(Status.TERMINATED.name())) {
            Event event = new Event();
            event.setCommited(LocalDateTime.now());
            event.setEvent(Status.TERMINATED.name());
            event.setRequest(request);
            request.setStatus(Status.TERMINATED.name());
            request.getEvents().add(event);
            request = requestRepository.save(request);
        }
        return request;
    }

    @Override
    public Request getDetailedRequest(int id) {
        return requestRepository.getById(id).orElseThrow();
    }

    @Override
    public List<Request> findByCriteria(RequestCriteria criteria) {
        return requestRepository.findAllSorted(criteria);
    }

    @Override
    public Document downloadFile(int id) {
        return documentRepository.getById(id).orElseThrow();
    }
}
