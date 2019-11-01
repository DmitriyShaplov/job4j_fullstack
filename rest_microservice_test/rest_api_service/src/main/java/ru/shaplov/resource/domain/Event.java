package ru.shaplov.resource.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author shaplov
 * @since 30.10.2019
 */
@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDateTime commited;

    private String event;

    @ManyToOne(fetch = FetchType.LAZY)
    private Request request;

    @JsonIgnore
    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getCommited() {
        return commited;
    }

    public String getEvent() {
        return event;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCommited(LocalDateTime commited) {
        this.commited = commited;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event1 = (Event) o;
        return id == event1.id &&
                Objects.equals(commited, event1.commited) &&
                Objects.equals(event, event1.event);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, commited, event);
    }
}
