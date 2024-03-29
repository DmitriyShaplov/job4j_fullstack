package ru.shaplov.auth.controller;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.shaplov.auth.domain.Person;

import java.util.List;

/**
 * @author shaplov
 * @since 01.10.2019
 */
@RestController
@RequestMapping("/person")
public class RestClientController {

    private static final String API = "http://localhost:8080/person/";
    private static final String API_ID = "http://localhost:8080/person/{id}";

    private final RestTemplate rest = new RestTemplate();

    @GetMapping("/")
    public List<Person> findAll() {
        return rest.exchange(
                API,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Person>>() {}
        ).getBody();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> findById(@PathVariable int id) {
        Person person = rest.getForObject(
                API_ID,
                Person.class,
                id
        );
        return ResponseEntity.ok(person);
    }

    @PostMapping("/")
    public ResponseEntity<Person> create(@RequestBody Person person) {
        person = rest.postForObject(API, person, Person.class);
        return new ResponseEntity<>(person, HttpStatus.CREATED);
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Person person) {
        rest.put(API, person);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        rest.delete(API_ID, id);
        return ResponseEntity.ok().build();
    }
}
