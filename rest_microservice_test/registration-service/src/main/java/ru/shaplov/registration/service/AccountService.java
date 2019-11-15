package ru.shaplov.registration.service;

import ru.shaplov.registration.dto.RequestCriteria;
import ru.shaplov.registration.dto.UserDto;
import ru.shaplov.registration.dto.UserRegistrationDto;
import ru.shaplov.registration.dto.UsersRequestsDto;

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

    /**
     * Retrieve Users from auth server and Requests from rest-api server.
     *
     * @return UsersAndRequest dto
     */
    UsersRequestsDto getUsersAndRequests(RequestCriteria criteria);
}
