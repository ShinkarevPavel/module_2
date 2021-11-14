package com.epam.esm.controller;

import com.epam.esm.dto.PageParameterDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.linkbuilder.LinkBuilder;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/users")
public class UserController {

    private final UserService userService;
    private final LinkBuilder<UserDto> linkBuilder;

    @Autowired
    public UserController(@Qualifier("jpaUserService") UserService userService, LinkBuilder<UserDto> linkBuilder) {
        this.userService = userService;
        this.linkBuilder = linkBuilder;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public UserDto create(@Validated @RequestBody UserDto userDto) {
        UserDto uDto = userService.create(userDto);
        linkBuilder.addLinks(uDto);
        return uDto;
    }

    @PatchMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ADMIN')")
    public UserDto update(@PathVariable Long id, @Validated(UserDto.Update.class) @RequestBody UserDto userDto) {
        userDto.setId(id);
        System.out.println(userDto);
        return userService.update(userDto);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public UserDto getById(@PathVariable Long userId) {
        UserDto userDto = userService.getById(userId);
        linkBuilder.addLinks(userDto);
        return userDto;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<UserDto> getAll(@Validated PageParameterDto pageParameterDto) {
        List<UserDto> users = userService.getAll(pageParameterDto);
        users.forEach(linkBuilder::addLinks);
        return users;
    }

    @DeleteMapping(value = "/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long userId) {
        userService.delete(userId);
    }
}
