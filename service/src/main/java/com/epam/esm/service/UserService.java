package com.epam.esm.service;

import com.epam.esm.dto.PageParameterDto;
import com.epam.esm.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto create(UserDto userDto);

    List<UserDto> getAll(PageParameterDto pageParameterDto);

    UserDto getById(long id);

    UserDto update(UserDto userDto);

    void delete(UserDto userDto);
}
