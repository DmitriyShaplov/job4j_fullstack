package ru.shaplov.resource.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.shaplov.resource.domain.Passport;
import ru.shaplov.resource.domain.Request;
import ru.shaplov.resource.service.RequestService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


/**
 * @author shaplov
 * @since 28.10.2019
 */
@RestController
@RequestMapping("/request")
public class RequestController {

    private final RequestService requestService;

    @Autowired
    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @PostMapping(consumes = "multipart/form-data;charset=UTF-8")
    public ResponseEntity<Request> postRequest(@Valid Passport passport,
                                               @RequestParam @NotBlank String serviceName,
                                               @RequestParam @Size(max = 10) MultipartFile[] files) {
        return new ResponseEntity<>(
                requestService.createRequest(passport, serviceName, files),
                HttpStatus.CREATED
        );
    }
}
