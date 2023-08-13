package com.nisemup.bakerymanager.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", length = 15, nullable = false, unique = true)
    private String name;

    @Column(name = "description", length = 50)
    private String description;

    @Column(name = "picture", length = 100)
    private String picture;

    @Column(name = "active", nullable = false, columnDefinition = "boolean default true")
    private Boolean active = true;
}
