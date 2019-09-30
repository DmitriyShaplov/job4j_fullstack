package ru.shaplov.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shaplov.auth.domain.Person;
import ru.shaplov.auth.repository.PersonRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author shaplov
 * @since 30.09.2019
 */
@RestController
@RequestMapping("/person")
public class PersonController {
    private final PersonRepository persons;

    @Autowired
    public PersonController(PersonRepository persons) {
        this.persons = persons;
    }

    @GetMapping("/")
    public List<Person> findAll() {
        return StreamSupport.stream(
                persons.findAll().spliterator(), false
        ).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> findById(@PathVariable int id) {
        var person = persons.findById(id);
        return new ResponseEntity<>(
                person.orElse(new Person()),
                person.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    @PostMapping("/")
    public ResponseEntity<Person> create(@RequestBody Person person) {
        return person.getId() != 0 ? ResponseEntity.badRequest().build()
            : new ResponseEntity<>(
                persons.save(person),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Person person) {
        if (persons.findById(person.getId()).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        persons.save(person);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Person person = new Person();
        person.setId(id);
        this.persons.delete(person);
        return ResponseEntity.ok().build();
    }
}
