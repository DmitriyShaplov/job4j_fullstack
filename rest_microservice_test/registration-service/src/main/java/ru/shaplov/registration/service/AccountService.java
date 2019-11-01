package ru.shaplov.registration.service;

import ru.shaplov.registration.dto.UserDto;
import ru.shaplov.registration.dto.UserRegistrationDto;

/**
 * @author shaplov
 * @since 29.10.2019
 */
public interface AccountService {

    /**
     * Invokes Auth Service user creation
     *
     * @param user
     * @return created account
     */
    UserDto create(UserRegistrationDto user);
}
