package ru.shaplov.resource.domain;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.Objects;

/**
 * @author shaplov
 * @since 30.10.2019
 */
@Entity
@Table(name = "passports")
public class Passport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Pattern(regexp = "\\d{4}")
    private String number;

    @NotNull
    @Pattern(regexp = "\\d{6}")
    private String series;

    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    @NotBlank
    private String patronymic;

    @NotNull
    @Pattern(regexp = "[MF]")
    private String sex;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthday;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "date_of_issue")
    private LocalDate dateOfIssue;

    @NotNull
    private String issued;

    @NotNull
    @Pattern(regexp = "\\d{6}")
    private String code;

    @NotBlank
    @Column(name = "place_of_birth")
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
