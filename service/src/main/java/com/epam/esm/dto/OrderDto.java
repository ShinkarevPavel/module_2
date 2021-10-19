package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private Long id;

    @NotNull
    @Digits(integer = 5, fraction = 2)
    @DecimalMin(value = "0.50")
    private Double cost;

    @NotNull
    private UserDto user;

    @JsonIgnore
    @Builder.Default
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime order_date = LocalDateTime.now();

    @Singular
    @JsonInclude(JsonInclude.Include.NON_NULL)
    List<GiftCertificateDto> certificates;
}
