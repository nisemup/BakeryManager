package com.nisemup.bakerymanager.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Embeddable
public class OrderDetail {

    @ManyToOne
    private Product productId;

    private Integer quantity;

    private BigDecimal total;

}
