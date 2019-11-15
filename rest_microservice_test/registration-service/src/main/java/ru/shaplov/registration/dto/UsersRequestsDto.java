package ru.shaplov.registration.dto;

import java.util.List;

/**
 * @author shaplov
 * @since 15.11.2019
 */
public class UsersRequestsDto {
    private final List<UserDto> users;
    private final List<Request> requests;

    public UsersRequestsDto(List<UserDto> users, List<Request> requests) {
        this.users = users;
        this.requests = requests;
    }

    public List<UserDto> getUsers() {
        return users;
    }

    public List<Request> getRequests() {
        return requests;
    }
}
