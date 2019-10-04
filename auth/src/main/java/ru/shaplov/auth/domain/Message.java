package ru.shaplov.auth.domain;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author shaplov
 * @since 02.10.2019
 */
@Entity
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String content;

    private LocalDateTime created;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @JsonGetter("room")
    protected Room getRoomJson() {
        Room r = null;
        if (room != null) {
            r = new Room();
            r.setId(room.getId());
            r.setName(room.getName());
        }
        return r;
    }

    @JsonGetter("person")
    protected Person getPersonJson() {
        Person p = null;
        if (person != null) {
            p = new Person();
            p.setId(person.getId());
            p.setLogin(person.getLogin());
        }
        return p;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public Room getRoom() {
        return room;
    }

    public Person getPerson() {
        return person;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return id == message.id &&
                Objects.equals(content, message.content) &&
                Objects.equals(created, message.created) &&
                Objects.equals(room, message.room) &&
                Objects.equals(person, message.person);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, created, room, person);
    }
}
