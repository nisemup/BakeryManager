package com.nisemup.bakerymanager.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "shipping_method")
public class ShippingMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long shippingMethodId;

    @Column(name = "name", nullable = false, length = 45, unique = true)
    private String shippingMethodName;
}
