package com.nisemup.bakerymanager.model;

import jakarta.persistence.*;

@Entity
@Table(name = "deliveries")
public class Deliver {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driverId;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order orderId;

    @Column(name = "status", nullable = false)
    private Boolean status;


}
