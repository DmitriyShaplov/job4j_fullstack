package ru.shaplov.registration.dto;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author shaplov
 * @since 29.10.2019
 */
public class UserRegistrationDto implements Serializable {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
