package com.epam.esm.hateos.impl;

import com.epam.esm.controller.UserController;
import com.epam.esm.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserLinkBuilder extends AbstractBuilder<UserDto> {

    @Override
    public void addLinks(UserDto userDto) {
        addIdLinks(UserController.class, userDto, userDto.getId(), GET, DELETE);
    }
}
