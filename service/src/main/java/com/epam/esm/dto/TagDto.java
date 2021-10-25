package com.epam.esm.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

public class TagDto extends RepresentationModel<TagDto> {

    private Long id;

    @NotEmpty
    @Size(min = 1, max = 45)
    private String name;
}
