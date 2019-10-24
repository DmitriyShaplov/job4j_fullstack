package ru.shaplov.auth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shaplov.auth.domain.Person;
import ru.shaplov.auth.domain.Role;
import ru.shaplov.auth.repository.PersonRepository;

import java.util.Collection;

/**
 * @author shaplov
 * @since 02.10.2019
 */
@Service
@Transactional(readOnly = true)
public class CustomUserDetailsService implements UserDetailsService {

    private final PersonRepository personRepository;

    @Autowired
    public CustomUserDetailsService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person = personRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Login " + username + " not found."));
        return new User(person.getLogin(), person.getPassword(), getAuthorities(person));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Person person) {
        String[] personRoles = person.getRoles().stream().map(Role::getName).toArray(String[]::new);
        return AuthorityUtils.createAuthorityList(personRoles);
    }
}
