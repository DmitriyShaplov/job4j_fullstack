package ru.shaplov.registration.dto;

import java.time.LocalDate;
import java.util.Objects;

/**
 * @author shaplov
 * @since 30.10.2019
 */
public class Passport {

    private int id;
    private String number;
    private String series;
    private String name;
    private String surname;
    private String patronymic;
    private String sex;
    private LocalDate birthday;
    private LocalDate dateOfIssue;
    private String issued;
    private String code;
    private String placeOfBirth;

    public int getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public String getSeries() {
        return series;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public String getSex() {
        return sex;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public LocalDate getDateOfIssue() {
        return dateOfIssue;
    }

    public String getIssued() {
        return issued;
    }

    public String getCode() {
        return code;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public void setDateOfIssue(LocalDate dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }

    public void setIssued(String issued) {
        this.issued = issued;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Passport passport = (Passport) o;
        return  Objects.equals(number, passport.number) &&
                Objects.equals(series, passport.series) &&
                Objects.equals(name, passport.name) &&
                Objects.equals(surname, passport.surname) &&
                Objects.equals(patronymic, passport.patronymic) &&
                Objects.equals(sex, passport.sex) &&
                Objects.equals(birthday, passport.birthday) &&
                Objects.equals(dateOfIssue, passport.dateOfIssue) &&
                Objects.equals(code, passport.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, series, name, surname, patronymic, sex, birthday, dateOfIssue, code);
    }
}
