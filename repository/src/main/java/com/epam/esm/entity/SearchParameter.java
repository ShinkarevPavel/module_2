package com.epam.esm.entity;

import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class SearchParameter {
    private List<String> tagName;
    private String searchPart;
    private List<String> fieldsForSort;
    private List<String> orderSort;
}
