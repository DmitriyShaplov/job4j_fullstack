package ru.shaplov.auth.dto;

import java.io.Serializable;

/**
 * @author shaplov
 * @since 29.10.2019
 */
public class UserRegistrationDto implements Serializable {

    private String username;
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
