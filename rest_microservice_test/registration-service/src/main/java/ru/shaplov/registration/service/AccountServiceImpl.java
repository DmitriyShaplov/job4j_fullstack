package ru.shaplov.registration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.shaplov.registration.dto.*;
import ru.shaplov.registration.feing.AuthServiceFeignClient;
import ru.shaplov.registration.feing.RestApiFeignClient;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author shaplov
 * @since 29.10.2019
 */
@Service
public class AccountServiceImpl implements AccountService {

    private final AuthServiceFeignClient authServiceFeignClient;
    private final RestApiFeignClient restApiFeignClient;

    @Autowired
    public AccountServiceImpl(AuthServiceFeignClient authServiceFeignClient,
                              RestApiFeignClient restApiFeignClient) {
        this.authServiceFeignClient = authServiceFeignClient;
        this.restApiFeignClient = restApiFeignClient;
    }

    @Override
    public UserDto create(UserRegistrationDto user) {
        UserDto userDto = null;
        try {
            userDto = authServiceFeignClient.createUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userDto;
    }

    @Override
    public UsersRequestsDto getUsersAndRequests(RequestCriteria criteria) {
        CompletableFuture<List<UserDto>> usersFuture = CompletableFuture.supplyAsync(
                () -> authServiceFeignClient.getUsers().getBody()
        );
        CompletableFuture<List<Request>> requestFuture = CompletableFuture.supplyAsync(
                () -> restApiFeignClient.getAllRequests().getBody()
        );
        CompletableFuture<UsersRequestsDto> result = usersFuture
                .thenCombine(requestFuture, UsersRequestsDto::new);
        try {
            return result.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            throw new IllegalStateException();
        }
    }
}
