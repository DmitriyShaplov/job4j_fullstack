package ru.shaplov.registration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.shaplov.registration.dto.RequestCriteria;
import ru.shaplov.registration.dto.UserDto;
import ru.shaplov.registration.dto.UserRegistrationDto;
import ru.shaplov.registration.dto.UsersRequestsDto;
import ru.shaplov.registration.service.AccountService;

import javax.validation.Valid;

/**
 * @author shaplov
 * @since 29.10.2019
 */
@RestController
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public UserDto createNewDepartmentAccount(@Valid @RequestBody UserRegistrationDto user) {
        return accountService.create(user);
    }

    @GetMapping
    public ResponseEntity<UsersRequestsDto> getRegisteredUsersAndRequests(
            @RequestBody(required = false) RequestCriteria criteria
    ) {
        return ResponseEntity.ok(accountService.getUsersAndRequests(criteria));
    }
}