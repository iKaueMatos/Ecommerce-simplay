package com.commerce.backend.modules.user.application.converter;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.commerce.backend.modules.user.application.useCases.dto.UserResponse;
import com.commerce.backend.modules.user.infra.entity.User;

@Component
public class UserResponseConverter implements Function<User, UserResponse> {
    @Override
    public UserResponse apply(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setEmail(user.getEmail());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setAddress(user.getAddress());
        userResponse.setCity(user.getCity());
        userResponse.setState(user.getState());
        userResponse.setZip(user.getZip());
        userResponse.setPhone(user.getPhone());
        userResponse.setCountry(user.getCountry());
        userResponse.setEmailVerified(user.getEmailVerified());
        return userResponse;
    }
}
