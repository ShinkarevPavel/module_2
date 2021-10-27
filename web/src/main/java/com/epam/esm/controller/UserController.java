package com.epam.esm.controller;

import com.epam.esm.dto.PageParameterDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.linkbuilder.LinkBuilder;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping
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

    @GetMapping
    public List<UserDto> getAll(@Validated PageParameterDto pageParameterDto) {
        List<UserDto> users = userService.getAll(pageParameterDto);
        users.forEach(linkBuilder::addLinks);
        return users;
    }
}
