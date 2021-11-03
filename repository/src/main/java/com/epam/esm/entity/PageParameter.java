package com.epam.esm.entity;

import lombok.*;

@Builder
@Getter
@AllArgsConstructor
public class PageParameter {

    private Integer page;
    private Integer size;
}
