package ru.shaplov.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.shaplov.auth.domain.Person;
import ru.shaplov.auth.service.PersonService;

import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = PersonController.class)
@AutoConfigureMockMvc
public class PersonControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PersonService persons;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void whenGetPersonsList() throws Exception {
        Person person = new Person();
        person.setId(1);
        person.setLogin("login");
        person.setPassword("password");
        given(
                persons.findAll()
        ).willReturn(List.of(person));
        mvc.perform(get("/person/"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        mapper.writeValueAsString(List.of(person))
                        )
                );
        verify(
                persons, times(1)
        ).findAll();
    }

    @Test
    @WithMockUser(username = "login")
    public void whenGetPersonById() throws Exception {
        Person person = new Person();
        person.setId(1);
        person.setLogin("login");
        person.setPassword("password");
        given(
                persons.findByLogin("login")
        ).willReturn(Optional.of(person));
        mvc.perform(get("/person/login"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        mapper.writeValueAsString(person)
                        )
                );
        verify(
                persons, times(1)
        ).findByLogin("login");
    }

    @Test
    public void whenCreatePerson() throws Exception {
        Person person = new Person();
        person.setLogin("login");
        person.setPassword("password");
        String requestData = mapper.writeValueAsString(person);
        Person result = new Person();
        result.setLogin("login");
        result.setPassword("password");
        result.setId(10);
        given(
                persons.save(person)
        ).willReturn(result);
        mvc.perform(post("/person/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestData))
                .andExpect(status().isCreated())
                .andExpect(content().string(
                        mapper.writeValueAsString(result)
                        )
                );
        verify(
                persons, times(1)
        ).save(person);
    }

    @Test
    public void whenCreatePersonWithExistsId() throws Exception {
        Person result = new Person();
        result.setLogin("login");
        result.setPassword("password");
        result.setId(10);
        String request = mapper.writeValueAsString(result);
        mvc.perform(post("/person/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(request))
                .andExpect(status().isBadRequest());
        verify(
                persons, times(0)
        ).save(result);
    }

    @Test
    @WithMockUser(username = "login")
    public void whenUpdatePerson() throws Exception {
        Person person = new Person();
        person.setId(10);
        person.setLogin("login");
        person.setPassword("password");
        String requestData = mapper.writeValueAsString(person);
        given(
                persons.findByLogin("login")
        ).willReturn(Optional.of(person));
        mvc.perform(put("/person/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestData))
                .andExpect(status().isOk());
        verify(
                persons, times(1)
        ).save(person);
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void whenUpdatePersonWithWrongId() throws Exception {
        Person person = new Person();
        person.setLogin("login");
        String requestData = mapper.writeValueAsString(person);
        mvc.perform(put("/person/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestData))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "login")
    public void whenDeletePerson() throws Exception {
        mvc.perform(
                delete("/person/login")
        ).andExpect(status().isOk());
        verify(
                persons, times(1)
        ).delete("login");
    }
}