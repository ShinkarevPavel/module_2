package com.epam.esm.dto;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

public class TagDto {

    private Long id;

    @NotEmpty
    @Size(min = 1, max = 45)
    private String name;
}
