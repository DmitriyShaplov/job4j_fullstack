package ru.shaplov.auth.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import ru.shaplov.auth.domain.EnumRole;
import ru.shaplov.auth.domain.Person;
import ru.shaplov.auth.domain.Role;
import ru.shaplov.auth.repository.PersonRepository;
import ru.shaplov.auth.repository.RoleRepository;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(SpringRunner.class)
public class PersonServiceTest {

    @TestConfiguration
    static class PersonServiceTestContextConfig {

        @Bean
        @Autowired
        public PersonService personService(PersonRepository personRepository, RoleRepository roleRepository, PasswordEncoder encoder) {
            return new PersonServiceImpl(personRepository, roleRepository, encoder);
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }
    }

    @Autowired
    private PersonService personService;

    @Autowired
    private PasswordEncoder encoder;

    @MockBean private PersonRepository personRepository;
    @MockBean private RoleRepository roleRepository;

    @Test
    public void whenSavePersonThenPasswordEncoded() {
        Person person = new Person();
        person.setLogin("login");
        person.setPassword("password");
        Role role = new Role();
        role.setName("ROLE_USER");
        when(personRepository.save(any(Person.class)))
                .thenAnswer(i -> i.getArguments()[0]);
        when(roleRepository.findByName(EnumRole.ROLE_USER.name()))
                .thenReturn(Optional.of(role));
        assertTrue(encoder.matches("password", personService.save(person).getPassword()));
    }

    @Test
    public void whenDelete() {
        personService.delete("login");
        verify(personRepository, times(1)).deleteByLogin("login");
    }

    @Test
    public void whenFindById() {
        personService.findByLogin("login");
        verify(personRepository, times(1)).findByLogin("login");
    }

    @Test
    public void whenFindAll() {
        personService.findAll();
        verify(personRepository, times(1)).findAll();
    }
}