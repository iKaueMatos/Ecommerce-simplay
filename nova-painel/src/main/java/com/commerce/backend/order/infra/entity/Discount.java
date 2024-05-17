package com.commerce.backend.order.infra.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

import com.commerce.backend.cart.infra.entity.Cart;

import java.util.List;

@Entity
@Table(name = "discount")
@Getter
@Setter
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
