package com.epam.esm.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class AuthenticationRequestDto {

    @NotEmpty
    @Size(min = 5, max = 45)
    private String username;
    
    @NotEmpty
    @Size(min = 8, max = 250)
    private String password;

}
