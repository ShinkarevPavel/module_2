package com.epam.esm.dto;

import com.epam.esm.entity.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.*;

@Builder
@Getter
@Setter
@EqualsAndHashCode(exclude = "id")
@AllArgsConstructor
@NoArgsConstructor
public class UserDto extends RepresentationModel<UserDto> {

    private Long id;
    @JsonIgnore
    private Role role;

    @Size(min = 8, max = 250)
    private String password;

    @NotEmpty
    @Size(min = 5, max = 45)
    private String username;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserDto{");
        sb.append("id=").append(id);
        sb.append(", username='").append(username).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
