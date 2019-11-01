package ru.shaplov.resource.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.shaplov.resource.domain.Document;
import ru.shaplov.resource.domain.Passport;
import ru.shaplov.resource.domain.Request;
import ru.shaplov.resource.domain.Status;
import ru.shaplov.resource.repository.PassportRepository;
import ru.shaplov.resource.repository.RequestRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author shaplov
 * @since 31.10.2019
 */
@Service
@Transactional
public class RequestServiceImpl implements RequestService {

    private final PassportRepository passportRepository;
    private final RequestRepository requestRepository;

    @Autowired
    public RequestServiceImpl(PassportRepository passportRepository, RequestRepository requestRepository) {
        this.passportRepository = passportRepository;
        this.requestRepository = requestRepository;
    }

    @Override
    public Request createRequest(Passport passport, String serviceName, MultipartFile[] files) {
        Request request = new Request();
        Optional<Passport> passportOptional = passportRepository.findByNumberAndSeries(
                passport.getNumber(), passport.getSeries()
        );
        if (passportOptional.isPresent()) {
            Passport passportPersisted = passportOptional.get();
            if (!passportPersisted.equals(passport)) {
                throw new IllegalStateException("Incorrect passport data");
            }
            request.setPassport(passportPersisted);
        } else {
            request.setPassport(passport);
        }
        Set<Document> documents = new HashSet<>();
        try {
            for (MultipartFile file : files) {
                if (file != null && file.getSize() > 0) {
                    Document doc = new Document();
                    doc.setData(file.getInputStream().readAllBytes());
                    doc.setMimeType(file.getContentType());
                    doc.setCreated(LocalDateTime.now());
                    doc.setName(file.getOriginalFilename());
                    doc.setRequest(request);
                    documents.add(doc);
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException("Unable to get files");
        }
        request.setDocuments(documents);
        request.setCreated(LocalDateTime.now());
        request.setServiceName(serviceName);
        request.setStatus(Status.PENDING.name());
        request = requestRepository.save(request);
        return request;
    }

    public static void main(String[] args) {
    }
}
