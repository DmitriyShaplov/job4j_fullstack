package ru.shaplov.registration.feing;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.shaplov.registration.dto.Request;
import ru.shaplov.registration.dto.RequestCriteria;

import java.util.List;

/**
 * @author shaplov
 * @since 15.11.2019
 */
@FeignClient(name = "department-service")
public interface RestApiFeignClient {

    @GetMapping(value = "/department/request")
    ResponseEntity<List<Request>> getAllRequests();
}
