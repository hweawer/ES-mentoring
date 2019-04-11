package com.epam.esm.authentication.service;

import com.epam.esm.authentication.entity.UserDto;

public interface UserService {
    UserDto create(UserDto userDto);
}
