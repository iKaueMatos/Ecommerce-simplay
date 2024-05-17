package com.commerce.backend.auth.infra.repository;

import com.commerce.backend.auth.infra.entity.PasswordForgotToken;
import com.commerce.backend.user.infra.entity.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordForgotTokenRepository extends CrudRepository<PasswordForgotToken, Long> {
    Optional<PasswordForgotToken> findByToken(String token);

    Optional<PasswordForgotToken> findByUser(User user);
}
