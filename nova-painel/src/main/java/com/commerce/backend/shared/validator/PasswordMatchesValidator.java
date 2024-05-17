package com.commerce.backend.shared.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.commerce.backend.user.application.useCases.dto.PasswordForgotValidateRequest;
import com.commerce.backend.user.application.useCases.dto.PasswordResetRequest;
import com.commerce.backend.user.application.useCases.dto.RegisterUserRequest;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        if (obj instanceof RegisterUserRequest) {
            RegisterUserRequest registerUserRequest = (RegisterUserRequest) obj;
            return registerUserRequest.getPassword().equals(registerUserRequest.getPasswordRepeat());
        }
        
        if (obj instanceof PasswordResetRequest) {
            PasswordResetRequest passwordResetRequest = (PasswordResetRequest) obj;
            return passwordResetRequest.getNewPassword().equals(passwordResetRequest.getNewPasswordConfirm());
        }
        
        if (obj instanceof PasswordForgotValidateRequest) {
            PasswordForgotValidateRequest passwordForgotValidateRequest = (PasswordForgotValidateRequest) obj;
            return passwordForgotValidateRequest.getNewPassword().equals(passwordForgotValidateRequest.getNewPasswordConfirm());
        }

        return false;
    }
}