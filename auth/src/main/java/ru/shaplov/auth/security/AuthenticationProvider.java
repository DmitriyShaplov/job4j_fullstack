package ru.shaplov.auth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.shaplov.auth.service.PersonService;

import java.util.Optional;

/**
 * @author shaplov
 * @since 08.10.2019
 */
@Component
public class AuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    private final PersonService personService;

    @Autowired
    public AuthenticationProvider(PersonService personService) {
        this.personService = personService;
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        Object token = authentication.getCredentials();
        return Optional.ofNullable(token)
                .map(String::valueOf)
                .flatMap(personService::findByToken)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Cannot find user with authentication token=" + token
                ));
    }
}
