package com.epam.esm.dto;

import com.epam.esm.entity.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    public interface Create {
    }

    public interface Update {
    }

    @Null(groups = Create.class)
    private Long id;

    @JsonIgnore
    @Builder.Default
    private Role role = Role.USER;

    @NotEmpty(groups = Create.class)
    @Size(min = 8, max = 250, groups = {Create.class, Update.class})
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NotEmpty(groups = Create.class)
    @Size(min = 5, max = 45, groups = {Create.class, Update.class})
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
