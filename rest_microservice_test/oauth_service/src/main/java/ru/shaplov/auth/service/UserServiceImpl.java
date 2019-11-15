package ru.shaplov.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shaplov.auth.domain.EnumRole;
import ru.shaplov.auth.domain.SimpleUser;
import ru.shaplov.auth.dto.UserDto;
import ru.shaplov.auth.dto.UserRegistrationDto;
import ru.shaplov.auth.repository.RoleRepository;
import ru.shaplov.auth.repository.UserRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author shaplov
 * @since 30.10.2019
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
    }

    @Override
    public UserDto createDepartmentEmployee(UserRegistrationDto userDto) {
        SimpleUser user = new SimpleUser();
        user.setUsername(userDto.getUsername());
        user.setPassword(this.encoder.encode(userDto.getPassword()));
        user.setRoles(Set.of(this.roleRepository.findByName(EnumRole.ROLE_EMPLOYEE.name()).orElseThrow()));
        user = this.userRepository.save(user);
        return new UserDto(user.getId(), user.getUsername());
    }

    @Override
    public List<UserDto> getUsers() {
        return userRepository.findAll().stream().map(
                v -> new UserDto(v.getId(), v.getUsername())
        )
                .collect(Collectors.toList());
    }
}
