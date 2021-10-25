package com.epam.esm.dto;

import com.epam.esm.validator.annotation.UserConstraint;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private Long id;

    @Null
    private Double cost;

    @UserConstraint
    private UserDto user;

    @Builder.Default
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime order_date = LocalDateTime.now();


    @JsonInclude(JsonInclude.Include.NON_NULL)
    List<GiftCertificateDto> certificates = new ArrayList<>();
}
