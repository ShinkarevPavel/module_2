package com.epam.esm.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 45, nullable = false)
    private String name;
//
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")           //Todo Hibirnate n+1 problem
//    private List<Order> orders = new ArrayList<>();
}