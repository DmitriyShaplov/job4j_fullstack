package ru.shaplov.resource.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.shaplov.resource.domain.Passport;
import ru.shaplov.resource.domain.Request;
import ru.shaplov.resource.service.DepartmentService;
import ru.shaplov.resource.service.RequestService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;


/**
 * @author shaplov
 * @since 28.10.2019
 */
@RestController
@RequestMapping("/request")
public class RequestController {

    private final RequestService requestService;
    private final DepartmentService departmentService;

    @Autowired
    public RequestController(RequestService requestService, DepartmentService departmentService) {
        this.requestService = requestService;
        this.departmentService = departmentService;
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

    @GetMapping
    @PreAuthorize("#oauth2.hasScope('create')")
    public ResponseEntity<List<Request>> getAllRequests() {
        return ResponseEntity.ok(departmentService.findByCriteria(null));
    }
}
