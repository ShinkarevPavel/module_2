package com.epam.esm.dto;

import com.epam.esm.validator.annotation.OrderCertificateConstraint;
import com.epam.esm.validator.annotation.UserConstraint;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@EqualsAndHashCode(exclude = {"id", "certificates"})
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto extends RepresentationModel<OrderDto> {

    private Long id;

    @Null
    private Double cost;

    @UserConstraint
    private UserDto user;

    @Builder.Default
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime order_date = LocalDateTime.now();

    @OrderCertificateConstraint
    @JsonInclude(JsonInclude.Include.NON_NULL)
    List<GiftCertificateDto> certificates = new ArrayList<>();

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OrderDto{");
        sb.append("id=").append(id);
        sb.append(", cost=").append(cost);
        sb.append(", user=").append(user);
        sb.append(", order_date=").append(order_date);
        sb.append(", certificates=").append(certificates);
        sb.append('}');
        return sb.toString();
    }
}
