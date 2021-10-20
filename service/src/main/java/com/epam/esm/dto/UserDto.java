package com.epam.esm.dto;

import lombok.*;


import javax.validation.constraints.*;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;
    @NotEmpty
    @Size(min = 1, max = 45)
    private String name;

//    private List<OrderDto> orders;
}
