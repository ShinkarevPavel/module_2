package com.epam.esm.security;

import com.epam.esm.entity.User;

public class SecurityUserDetailsBuilder {

    public static SecurityUserDetails create(User user) {
        return new SecurityUserDetails(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getRole().getAuthorities().stream().toList(),
                true
        );
    }
}
