package ru.shaplov.resource.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author shaplov
 * @since 30.10.2019
 */
@Entity
@Table(name = "documents")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic(fetch = FetchType.LAZY)
    private byte[] data;

    private String name;

    @Column(name = "mime_type")
    private String mimeType;

    private LocalDateTime created;

    @ManyToOne(fetch = FetchType.LAZY)
    private Request request;

    public int getId() {
        return id;
    }

    @JsonIgnore
    public byte[] getData() {
        return data;
    }

    public String getMimeType() {
        return mimeType;
    }

    @JsonIgnore
    public Request getRequest() {
        return request;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Document document = (Document) o;
        return id == document.id &&
                Arrays.equals(data, document.data) &&
                Objects.equals(name, document.name) &&
                Objects.equals(mimeType, document.mimeType) &&
                Objects.equals(created, document.created) &&
                Objects.equals(request, document.request);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, name, mimeType, created, request);
        result = 31 * result + Arrays.hashCode(data);
        return result;
    }
}
