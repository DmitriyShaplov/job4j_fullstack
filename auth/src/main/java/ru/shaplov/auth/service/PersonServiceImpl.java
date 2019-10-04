package ru.shaplov.auth.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shaplov.auth.domain.EnumRole;
import ru.shaplov.auth.domain.Person;
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
}
