package com.commerce.backend.order.infra.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

import com.commerce.backend.product.infra.entity.ProductVariant;

@Entity
@Table(name = "order_detail")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "order")
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_variant_id")
    private ProductVariant productVariant;

    @Column(name = "amount")
    private Integer amount;

}
