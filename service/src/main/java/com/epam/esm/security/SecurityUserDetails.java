package com.epam.esm.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class SecurityUserDetails implements UserDetails {
    private final Long userId;
    private final String username;
    private final String password;
    private final List<? extends GrantedAuthority> authorities;
    private final boolean isActive;

    public SecurityUserDetails(Long userId, String userName, String password, List<? extends GrantedAuthority> authorities, boolean isActive) {
        this.userId = userId;
        this.username = userName;
        this.password = password;
        this.authorities = authorities;
        this.isActive = isActive;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isActive;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isActive;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }

    public Long getUserId() {
        return userId;
    }
}
