package ru.shaplov.registration.feing;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.shaplov.registration.dto.UserDto;
import ru.shaplov.registration.dto.UserRegistrationDto;

/**
 * @author shaplov
 * @since 29.10.2019
 */
@FeignClient(name = "auth-service")
public interface AuthServiceFeignClient {

    @PostMapping(value = "/uaa/user")
    UserDto createUser(@RequestBody UserRegistrationDto user);
}
