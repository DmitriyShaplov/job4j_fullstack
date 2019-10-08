package ru.shaplov.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.shaplov.auth.domain.Person;
import ru.shaplov.auth.service.PersonService;

/**
 * @author shaplov
 * @since 08.10.2019
 */
@RestController
public class PersonSaveController {

    private final PersonService personService;

    @Autowired
    public PersonSaveController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping("/create")
    public ResponseEntity<Person> create(@RequestBody Person person) {
        return person.getId() != 0 ? ResponseEntity.badRequest().build()
                : new ResponseEntity<>(
                personService.save(person),
                HttpStatus.CREATED
        );
    }
}
