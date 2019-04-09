package com.epam.esm.service.impl;

import com.epam.esm.entity.Role;
import com.epam.esm.entity.User;
import com.epam.esm.repository.RoleRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.CustomUserDetails;
import com.epam.esm.service.UserService;
import com.epam.esm.service.dto.UserDto;
import com.epam.esm.service.dto.UserMapper;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.service.exception.LoginConstraintException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.*;

@Transactional
@Service(value = "userService")
public class UserServiceDatabase implements UserDetailsService, UserService {
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    private static final String USER_ROLE = "USER";

    public UserServiceDatabase(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDto create(UserDto userDto) {
        User user = UserMapper.INSTANCE.toEntity(userDto);
        if (userRepository.findUserByLogin(user.getLogin()).isPresent()) {
            throw new LoginConstraintException("");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = roleRepository.findByName(USER_ROLE).orElseThrow(() -> new RuntimeException(""));
        user.getRoles().add(role);
        return UserMapper.INSTANCE.toDto(user);
    }

    @Override
    public UserDto findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException(""));
        return UserMapper.INSTANCE.toDto(user);
    }

    @Override
    public UserDto findByLogin(String login) {
        User user = userRepository.findUserByLogin(login).orElseThrow(() -> new RuntimeException(""));
        return UserMapper.INSTANCE.toDto(user);
    }

    @Override
    public void delete(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(""));
        userRepository.delete(user);
    }

    @Override
    public List<UserDto> findAll(Integer page, Integer limit) {
        return userRepository.findAll(page, limit)
                .map(UserMapper.INSTANCE::toDto)
                .collect(toList());
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = userRepository.findUserByLogin(userId).orElseThrow(() -> new UsernameNotFoundException("Invalid username or password."));
        return new CustomUserDetails(user);
    }
}
