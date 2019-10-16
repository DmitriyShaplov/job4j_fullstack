package ru.shaplov.example.domain;

import java.util.Objects;

/**
 * @author shaplov
 * @since 16.10.2019
 */
public class Msg {
    private int userId;
    private String name;
    private String text;

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Msg msg = (Msg) o;
        return userId == msg.userId &&
                Objects.equals(name, msg.name) &&
                Objects.equals(text, msg.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, name, text);
    }
}
