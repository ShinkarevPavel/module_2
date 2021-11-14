package com.epam.esm.controller;

import com.epam.esm.dto.AuthenticationRequestDto;
import com.epam.esm.dto.JwtDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import com.epam.esm.exception.JwtAuthenticationException;
import com.epam.esm.linkbuilder.impl.UserLinkBuilder;
import com.epam.esm.security.JwtTokenProvider;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v3/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final UserLinkBuilder userLinkBuilder;

    public AuthenticationController(AuthenticationManager authenticationManager,
                                    JwtTokenProvider jwtTokenProvider,
                                    @Qualifier("jpaUserService") UserService userService,
                                    UserLinkBuilder userLinkBuilder) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.userLinkBuilder = userLinkBuilder;
    }

    @PostMapping("/login")
    public JwtDto authenticate(@RequestBody AuthenticationRequestDto requestDto) throws JwtAuthenticationException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestDto.getUsername(), requestDto.getPassword()));
            User user = userService.getByUsername(requestDto.getUsername());
            String token = jwtTokenProvider.createToken(requestDto.getUsername(), user.getRole().name());
            JwtDto jwtDto = JwtDto.builder().username(requestDto.getUsername()).token(token).build();
            return jwtDto;
        } catch (AuthenticationException e) {
            throw new JwtAuthenticationException("auth.error.incorrect_auth_pair", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }

    @PostMapping("/signup")
    public UserDto signUp(@Validated (UserDto.Create.class) @RequestBody UserDto userDto) {
        UserDto creatableUser = userService.create(userDto);
        userLinkBuilder.addLinks(creatableUser);
        return creatableUser;
    }
}
