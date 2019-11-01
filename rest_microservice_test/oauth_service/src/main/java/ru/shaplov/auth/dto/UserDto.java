package ru.shaplov.auth.dto;

import java.io.Serializable;

/**
 * @author shaplov
 * @since 29.10.2019
 */
public class UserDto implements Serializable {

    private int id;
    private String username;

    public UserDto(int id, String username) {
        this.id = id;
        this.username = username;
    }

    public UserDto() {
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
