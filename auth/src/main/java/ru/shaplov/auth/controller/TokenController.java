package ru.shaplov.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.shaplov.auth.service.PersonService;

/**
 * @author shaplov
 * @since 08.10.2019
 */
@RestController
public class TokenController {

    private final PersonService personService;

    @Autowired
    public TokenController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping("/token")
    public String getToken(@RequestParam("login") final String login,
                           @RequestParam("password") final String password) {
        return personService.login(login, password);
    }
}
