package ru.shaplov.auth.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shaplov.auth.domain.EnumRole;
import ru.shaplov.auth.domain.Person;
import ru.shaplov.auth.domain.Role;
import ru.shaplov.auth.exception.ForbiddenException;
import ru.shaplov.auth.repository.PersonRepository;
import ru.shaplov.auth.repository.RoleRepository;

import java.util.*;

/**
 * @author shaplov
 * @since 02.10.2019
 */
@Service
@Transactional
public class PersonServiceImpl implements PersonService {

    private static final Logger LOG = LogManager.getLogger(PersonServiceImpl.class);

    private final PersonRepository personRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository, RoleRepository roleRepository, PasswordEncoder encoder) {
        this.personRepository = personRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
    }

    @Override
    public List<Person> findAll() {
        return personRepository.findAll();
    }

    @Override
    public Optional<Person> findByLogin(String login) {
        return personRepository.findByLogin(login);
    }

    @Override
    public Person save(Person person) {
        person.setPassword(encoder.encode(person.getPassword()));
        person.setRoles(new HashSet<>(Set.of(
                roleRepository.findByName(EnumRole.ROLE_USER.name()).orElseThrow()
        )));
        personRepository.save(person);
        LOG.info("Person {} saved with id = {}", person.getLogin(), person.getId());
        return person;
    }

    @Override
    public void delete(String login) {
        personRepository.deleteByLogin(login);
        LOG.info("Person {} deleted", login);
    }

    @Override
    public String login(String login, String password) {
        Optional<Person> optPerson = personRepository.findByLogin(login);
        if (optPerson.isPresent()) {
            Person person = optPerson.get();
            if (encoder.matches(password, person.getPassword())) {
                String token = UUID.randomUUID().toString();
                person.setToken(token);
                personRepository.save(person);
                return token;
            }
        }
        throw new ForbiddenException();
    }

    @Override
    public Optional<User> findByToken(String token) {
        Optional<Person> optionalPerson = personRepository.findByToken(token);
        if (optionalPerson.isPresent()) {
            Person person = optionalPerson.get();
            User user = new User(person.getLogin(), person.getPassword(), getAuthorities(person));
            return Optional.of(user);
        }
        return Optional.empty();
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Person person) {
        String[] personRoles = person.getRoles().stream().map(Role::getName).toArray(String[]::new);
        return AuthorityUtils.createAuthorityList(personRoles);
    }
}
