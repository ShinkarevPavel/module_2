package com.epam.esm.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.Table;
import java.util.Set;


@Table(name = "roles")
public enum Role {

    USER("U"),
    ADMIN("A");

    private String code;

    Role(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public Set<GrantedAuthority> getAuthorities() {
        return Set.of(new SimpleGrantedAuthority(name()));
    }
}
