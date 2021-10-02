package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GiftCertificateDto {

    private Long id;

    private String name;

    private String description;

    private Double price;

    private Integer duration;
    @JsonIgnore
    @Builder.Default
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime createDate = LocalDateTime.now();
    @JsonIgnore
    @Builder.Default
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime lastUpdateDate = LocalDateTime.now();
    @Singular
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<TagDto> tags;
}