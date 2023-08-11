package com.nisemup.bakerymanager.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "payment_method")
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "type", nullable = false, unique = true)
    private String type;

    @Column(name = "allowed", nullable = false, columnDefinition = "boolean default true")
    private Boolean allowed;
}
