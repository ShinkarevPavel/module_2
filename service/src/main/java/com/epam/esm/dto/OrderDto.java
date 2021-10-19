package com.epam.esm.dto;

import com.epam.esm.validator.OrderCertificateConstraint;
import com.epam.esm.validator.UserConstraint;
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

    @Null
    private Double cost;

    @UserConstraint
    private UserDto user;

    @JsonIgnore
    @Builder.Default
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime order_date = LocalDateTime.now();

    @OrderCertificateConstraint
    @JsonInclude(JsonInclude.Include.NON_NULL)
    List<GiftCertificateDto> certificates;
}
