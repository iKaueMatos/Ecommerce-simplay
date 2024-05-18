package com.commerce.backend.modules.order.infra.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

import com.commerce.backend.modules.cart.infra.entity.Cart;

import java.util.List;

@Data
@Entity
@Table(name = "discount")
@NoArgsConstructor
@ToString(exclude = {"orderList", "cartList"})
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToMany(mappedBy = "discount")
    private List<Order> orderList;

    @OneToMany(mappedBy = "discount")
    private List<Cart> cartList;

    @Column(name = "code")
    private String code;

    @Column(name = "discount_percent")
    private Integer discountPercent;

    @Column(name = "status")
    private Integer status;
}
