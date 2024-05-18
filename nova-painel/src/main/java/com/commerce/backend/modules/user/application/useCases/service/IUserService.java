package com.commerce.backend.modules.user.application.useCases.service;

import com.commerce.backend.modules.user.application.useCases.dto.PasswordResetRequest;
import com.commerce.backend.modules.user.application.useCases.dto.RegisterUserRequest;
import com.commerce.backend.modules.user.application.useCases.dto.UpdateUserAddressRequest;
import com.commerce.backend.modules.user.application.useCases.dto.UpdateUserRequest;
import com.commerce.backend.modules.user.application.useCases.dto.UserResponse;
import com.commerce.backend.modules.user.infra.entity.User;

public interface IUserService {
    User register(RegisterUserRequest registerUserRequest);
    UserResponse fetchUser();
    User getUser();
    User saveUser(User user);
    User findByEmail(String email);
    boolean userExists(String email);
    UserResponse updateUser(UpdateUserRequest updateUserRequest);
    UserResponse updateUserAddress(UpdateUserAddressRequest updateUserAddressRequest);
    void resetPassword(PasswordResetRequest passwordResetRequest);
    Boolean getVerificationStatus();
}
