package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class JwtDto {
    private String username;
    private String token;


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("JwtDto{");
        sb.append("username='").append(username).append('\'');
        sb.append(", token='").append(token).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
