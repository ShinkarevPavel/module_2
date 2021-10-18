package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.validation.constraints.*;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    //Todo
    public interface Update {}
    public interface Create {}

    @NotNull(groups = Update.class)
    private Long id;

    @Size(min = 1, max = 45, groups = Create.class)
    @Size(min = 1, max = 45, groups = Update.class)
    @NotBlank
    private String name;

    private List<OrderDto> orders;
}
