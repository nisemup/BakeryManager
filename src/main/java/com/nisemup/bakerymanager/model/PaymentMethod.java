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
    @Column(name = "payment_id")
    private Long paymentId;

    @Column(name = "payment_type", nullable = false, unique = true)
    private String paymentType;

    @Column(name = "payment_allowed", nullable = false, columnDefinition = "boolean default true")
    private Boolean paymentAllowed;
}
