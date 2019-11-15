package ru.shaplov.registration.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * @author shaplov
 * @since 01.11.2019
 */
public class RequestCriteria {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;
    private String service;
    private String status;
    private String surname;
    private String name;
    private String patronymic;
    private boolean dateSort;
    private boolean serviceSort;
    private boolean statusSort;
    private boolean surnameSort;
    private boolean nameSort;
    private boolean patronymicSort;

    public LocalDate getDate() {
        return date;
    }

    public String getService() {
        return service;
    }

    public String getStatus() {
        return status;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public boolean isDateSort() {
        return dateSort;
    }

    public boolean isServiceSort() {
        return serviceSort;
    }

    public boolean isStatusSort() {
        return statusSort;
    }

    public boolean isSurnameSort() {
        return surnameSort;
    }

    public boolean isNameSort() {
        return nameSort;
    }

    public boolean isPatronymicSort() {
        return patronymicSort;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setService(String service) {
        this.service = service;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public void setDateSort(boolean dateSort) {
        this.dateSort = dateSort;
    }

    public void setServiceSort(boolean serviceSort) {
        this.serviceSort = serviceSort;
    }

    public void setStatusSort(boolean statusSort) {
        this.statusSort = statusSort;
    }

    public void setSurnameSort(boolean surnameSort) {
        this.surnameSort = surnameSort;
    }

    public void setNameSort(boolean nameSort) {
        this.nameSort = nameSort;
    }

    public void setPatronymicSort(boolean patronymicSort) {
        this.patronymicSort = patronymicSort;
    }
}
