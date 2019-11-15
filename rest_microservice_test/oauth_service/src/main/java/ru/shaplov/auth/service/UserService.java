package ru.shaplov.auth.service;

import ru.shaplov.auth.dto.UserDto;
import ru.shaplov.auth.dto.UserRegistrationDto;

import java.util.List;

/**
 * @author shaplov
 * @since 30.10.2019
 */
public interface UserService {
    /**
     * Creates new department employee user.
     * @param userDto user dto.
     * @return registered user dto.
     */
    UserDto createDepartmentEmployee(UserRegistrationDto userDto);

    /**
     * Retrieve all users.
     * @return all users.
     */
    List<UserDto> getUsers();
}
