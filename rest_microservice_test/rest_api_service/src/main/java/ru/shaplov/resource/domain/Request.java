package ru.shaplov.resource.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author shaplov
 * @since 30.10.2019
 */
@Entity
@Table(name = "requests")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    @Column(name = "service_name")
    private String serviceName;
    private String status;
    private LocalDateTime created;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "passport_id")
    private Passport passport;

    @OneToMany(mappedBy = "request",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<Document> documents = new HashSet<>();

    @OneToMany(mappedBy = "request",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Event> events = new ArrayList<>();

    public int getId() {
        return id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public Passport getPassport() {
        return passport;
    }

    public Set<Document> getDocuments() {
        return documents;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setPassport(Passport passport) {
        this.passport = passport;
    }

    public void setDocuments(Set<Document> documents) {
        this.documents = documents;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return id == request.id &&
                Objects.equals(serviceName, request.serviceName) &&
                Objects.equals(status, request.status) &&
                Objects.equals(created, request.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, serviceName, status, created);
    }
}
