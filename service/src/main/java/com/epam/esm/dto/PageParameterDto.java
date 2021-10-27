package com.epam.esm.dto;

import lombok.*;

import javax.validation.constraints.Min;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PageParameterDto {

    @Min(0)
    @Builder.Default
    private Integer page = 0;

    @Min(1)
    @Builder.Default
    private Integer size = 5;
}
