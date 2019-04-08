package com.epam.esm.service;

import com.epam.esm.service.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto create(UserDto userDto);
    UserDto findById(Long id);
    UserDto findByLogin(String login);
    void delete(Long id);
    List<UserDto> findAll(Integer page, Integer limit);
}
