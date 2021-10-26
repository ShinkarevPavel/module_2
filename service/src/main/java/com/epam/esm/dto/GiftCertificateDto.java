package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = "id")
public class GiftCertificateDto extends RepresentationModel<GiftCertificateDto> {

    private Long id;

    public interface Create {
    }

    public interface Update {
    }

    @NotEmpty(groups = Create.class)
    @Size(min = 1, max = 45, groups = {Create.class, Update.class})
    private String name;

    @NotNull(groups = Create.class)
    @Size(min = 1, max = 1000, groups = {Create.class, Update.class})
    private String description;

    @NotNull(groups = {Create.class})
    @Digits(integer = 5, fraction = 2, groups = {Create.class, Update.class})
    @DecimalMin(groups = {Create.class, Update.class},value = "0.00")
    private Double price;

    @NotNull(groups = Create.class)
    @Min(value = 1, groups = {Create.class, Update.class})
    @Max(value = 1000, groups = {Create.class, Update.class})
    private Integer duration;

    @JsonIgnore
    @Builder.Default
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime createDate = LocalDateTime.now();

    @Builder.Default
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime lastUpdateDate = LocalDateTime.now();

    @Singular
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<TagDto> tags;
}