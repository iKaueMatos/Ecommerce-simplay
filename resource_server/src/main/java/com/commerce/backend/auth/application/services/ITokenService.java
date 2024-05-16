package com.commerce.backend.auth.application.services;

import com.commerce.backend.user.application.useCases.dto.PasswordForgotValidateRequest;
import com.commerce.backend.user.infra.entity.User;

public interface ITokenService {
    void createEmailConfirmToken(User user);
    void createPasswordResetToken(String email);
    void validateEmail(String token);
    void validateForgotPasswordConfirm(String token);
    void validateForgotPassword(PasswordForgotValidateRequest passwordForgotValidateRequest);
}
