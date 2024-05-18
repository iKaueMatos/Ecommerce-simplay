package com.commerce.backend.modules.auth.infra.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import com.commerce.backend.modules.user.infra.entity.User;

import java.util.Date;

@Data
@Entity
@Table(name = "password_reset_token")
@NoArgsConstructor
@ToString
public class PasswordForgotToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "token")
    private String token;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    @Column(name = "expiry_date")
    private Date expiryDate;
}