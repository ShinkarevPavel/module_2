package com.epam.esm.security;

import com.epam.esm.entity.User;

import java.util.stream.Collectors;

public class SecurityUserDetailsBuilder {

    private SecurityUserDetailsBuilder() {
    }

    public static SecurityUserDetails create(User user) {
        return new SecurityUserDetails(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getRole().getAuthorities().stream().collect(Collectors.toList()),
                true
        );
    }
}
