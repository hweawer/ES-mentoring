package com.epam.esm.authentication.service;

import com.epam.esm.authentication.entity.*;
import com.epam.esm.authentication.exception.DuplicateLoginException;
import com.epam.esm.authentication.repository.RoleRepository;
import com.epam.esm.authentication.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private PasswordEncoder encoder;
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    private static final String USER_ROLE = "USER";

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
    }

    @Transactional
    @Override
    public UserDto create(UserDto userDto) {
        User user = UserMapper.INSTANCE.toEntity(userDto);
        if (userRepository.findUserByUsername(user.getLogin()).isPresent()) {
            throw new DuplicateLoginException("");
        }
        Role role = roleRepository.findByName(USER_ROLE).orElseThrow(() -> new RuntimeException(""));
        user.setPassword(encoder.encode(user.getPassword()));
        user.getRoles().add(role);
        user.setMoney(new BigDecimal(0));
        userRepository.create(user);
        return UserMapper.INSTANCE.toDto(user);
    }

    @Override
    public UserDto findById(Long id) {
        return null;
    }

    @Override
    public UserDto findByLogin(String login) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<UserDto> findAll(Integer page, Integer limit) {
        return null;
    }
}
