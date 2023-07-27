package com.nisemup.bakerymanager.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "category_name", length = 15, nullable = false, unique = true)
    private String categoryName;

    @Column(name = "category_description", length = 50)
    private String categoryDescription;

    @Column(name = "picture", length = 100)
    private String picture;

    @Column(name = "active", nullable = false, columnDefinition = "boolean default true")
    private Boolean active;
}
