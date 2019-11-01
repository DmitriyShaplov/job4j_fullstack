package ru.shaplov.resource.service;

import org.springframework.web.multipart.MultipartFile;
import ru.shaplov.resource.domain.Passport;
import ru.shaplov.resource.domain.Request;

/**
 * @author shaplov
 * @since 31.10.2019
 */
public interface RequestService {
    Request createRequest(Passport passport, String serviceName, MultipartFile[] files);
}
