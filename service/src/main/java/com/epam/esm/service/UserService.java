package com.epam.esm.service;

import com.epam.esm.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto create(UserDto userDto);

    List<UserDto> getAll();

    UserDto getById(long id);

    void update(UserDto userDto);

    void delete(UserDto userDto);
}
