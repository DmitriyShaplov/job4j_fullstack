package ru.shaplov.resource.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shaplov.resource.domain.Document;
import ru.shaplov.resource.domain.Request;
import ru.shaplov.resource.dto.RequestCriteria;
import ru.shaplov.resource.service.DepartmentService;

import java.util.List;

/**
 * @author shaplov
 * @since 01.11.2019
 */
@RestController
@RequestMapping("/api")
public class DepartmentController {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public ResponseEntity<List<Request>> getAllRequests(@RequestBody(required = false) RequestCriteria criteria) {
        return ResponseEntity.ok(departmentService.findByCriteria(criteria));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Request> terminate(@PathVariable int id) {
        return ResponseEntity.ok(departmentService.terminate(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Request> getDetailedRequest(@PathVariable int id) {
        return ResponseEntity.ok(departmentService.getDetailedRequest(id));
    }

    @GetMapping("/file/{id}")
    public ResponseEntity<byte[]> downloadDoc(@PathVariable int id) {
        Document document = departmentService.downloadFile(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(document.getMimeType()));
        headers.setContentLength(document.getData().length);
        headers.setContentDispositionFormData("attachment", document.getName());
        return new ResponseEntity<>(document.getData(), headers, HttpStatus.OK);
    }
}
