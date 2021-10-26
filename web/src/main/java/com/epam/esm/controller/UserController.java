package com.epam.esm.controller;

import com.epam.esm.dto.UserDto;
import com.epam.esm.hateos.LinkBuilder;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/users")
public class UserController {

    private final UserService userService;
    private final LinkBuilder<UserDto> linkBuilder;

    @Autowired
    public UserController(UserService userService, LinkBuilder<UserDto> linkBuilder) {
        this.userService = userService;
        this.linkBuilder = linkBuilder;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@Validated @RequestBody UserDto userDto) {
        UserDto uDto = userService.create(userDto);
        linkBuilder.addLinks(uDto);
        return uDto;
    }


    @PatchMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable Long id, @Validated @RequestBody UserDto userDto) {
        userDto.setId(id);
        userService.update(userDto);
    }

    @GetMapping(value = "/{id}")
    public UserDto getById(@PathVariable Long id) {
        UserDto userDto = userService.getById(id);
        linkBuilder.addLinks(userDto);
        return userDto;
    }
}
