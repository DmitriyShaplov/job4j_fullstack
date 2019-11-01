package ru.shaplov.registration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.shaplov.registration.dto.UserDto;
import ru.shaplov.registration.dto.UserRegistrationDto;
import ru.shaplov.registration.feing.AuthServiceFeignClient;

/**
 * @author shaplov
 * @since 29.10.2019
 */
@Service
public class AccountServiceImpl implements AccountService {

    private final AuthServiceFeignClient authServiceFeignClient;

    @Autowired
    public AccountServiceImpl(AuthServiceFeignClient authServiceFeignClient) {
        this.authServiceFeignClient = authServiceFeignClient;
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
}
