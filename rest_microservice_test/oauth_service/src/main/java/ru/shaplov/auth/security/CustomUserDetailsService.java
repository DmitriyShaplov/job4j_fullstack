package ru.shaplov.auth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.shaplov.auth.domain.Role;
import ru.shaplov.auth.domain.SimpleUser;
import ru.shaplov.auth.repository.UserRepository;

import java.util.Collection;

/**
 * @author shaplov
 * @since 26.10.2019
 */
@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SimpleUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No user with username + " + username));
        return new User(user.getUsername(), user.getPassword(), getAuthorities(user));
    }

    private Collection<GrantedAuthority> getAuthorities(SimpleUser user) {
        String[] roles = user.getRoles().stream().map(Role::getName).toArray(String[]::new);
        return AuthorityUtils.createAuthorityList(roles);
    }
}
