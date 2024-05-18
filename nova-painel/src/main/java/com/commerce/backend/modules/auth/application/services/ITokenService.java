package com.commerce.backend.modules.auth.application.services;

import com.commerce.backend.modules.user.application.useCases.dto.PasswordForgotValidateRequest;
import com.commerce.backend.modules.user.infra.entity.User;

public interface ITokenService {
    void createEmailConfirmToken(User user);
    void createPasswordResetToken(String email);
    boolean validateEmail(String token);
    void validateForgotPasswordConfirm(String token);
    void validateForgotPassword(PasswordForgotValidateRequest passwordForgotValidateRequest);
    boolean validateToken(String token);
}
