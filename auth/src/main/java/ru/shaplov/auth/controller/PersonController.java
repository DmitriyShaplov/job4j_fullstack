package ru.shaplov.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.shaplov.auth.domain.Person;
import ru.shaplov.auth.service.PersonService;

import java.util.List;

/**
 * @author shaplov
 * @since 30.09.2019
 */
@RestController
@RequestMapping("/person")
public class PersonController {
    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<Person> findAll() {
        return personService.findAll();
    }

    @GetMapping("/{login}")
    @PreAuthorize("#login == principal.username or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Person> findByLogin(@PathVariable String login) {
        var person = personService.findByLogin(login);
        return new ResponseEntity<>(
                person.orElse(new Person()),
                person.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    @PostMapping("/")
    public ResponseEntity<Person> create(@RequestBody Person person) {
        return person.getId() != 0 ? ResponseEntity.badRequest().build()
            : new ResponseEntity<>(
                personService.save(person),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/")
    @PreAuthorize("#person.login == principal.username or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> update(@RequestBody Person person) {
        if (personService.findByLogin(person.getLogin()).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        personService.save(person);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{login}")
    @PreAuthorize("#login == principal.username or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable String login) {
        this.personService.delete(login);
        return ResponseEntity.ok().build();
    }
}
