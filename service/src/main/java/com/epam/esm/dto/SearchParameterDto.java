package com.epam.esm.dto;

import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class SearchParameterDto {

    private List<String> tagName;

    private String searchPart;

    private List<String> fieldsForSort;

    private List<String> orderSort;
}
