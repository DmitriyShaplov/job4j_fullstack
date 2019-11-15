package ru.shaplov.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.shaplov.auth.dto.UserDto;
import ru.shaplov.auth.dto.UserRegistrationDto;
import ru.shaplov.auth.service.UserService;

import java.security.Principal;
import java.util.List;

/**
 * @author shaplov
 * @since 29.10.2019
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @PreAuthorize("#oauth2.hasScope('create')")
    public UserDto createUser(@RequestBody UserRegistrationDto user) {
        return this.userService.createDepartmentEmployee(user);
    }

    @GetMapping("/current")
    public Principal getUser(Principal principal) {
        return principal;
    }

    @GetMapping
    @PreAuthorize("#oauth2.hasScope('create')")
    public ResponseEntity<List<UserDto>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }
}
