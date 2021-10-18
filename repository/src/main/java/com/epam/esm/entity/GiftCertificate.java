package com.epam.esm.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "gift_certificate")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GiftCertificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column( length = 45, nullable = false)
    private String name;

    @Column(length = 8, nullable = false)
    private String description;

    @Column()
    private Double price;

    @Column(nullable = false)
    private Integer duration;

    @Column(name = "create_date",nullable = false, updatable = false)
    private LocalDateTime createDate;

    @Column(name = "last_ipdate_date",nullable = false)
    private LocalDateTime lastUpdateDate;

    @ManyToMany
    @JoinTable(
            name = "tag_certificate_associate",
            joinColumns = @JoinColumn(name = "gift_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
    private List<Tag> tags = new ArrayList<>();

    public void addTag(Tag tag) {
        tags.add(tag);
    }

}