package com.pavelshinkarev.entity;

import lombok.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component("gift")
//@Scope("prototype") ToDo
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GiftCertificate {

    private long id;

    private String name;

    private String description;

    private double price;

    private Integer duration;

    private LocalDateTime createDate;

    private LocalDateTime lastUpdateDate;

    @Singular
    private List<Tag> tags;

}