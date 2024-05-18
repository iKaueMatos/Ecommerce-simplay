package com.commerce.backend.modules.auth.domain.event;

import lombok.Data;
import org.springframework.context.ApplicationEvent;

import com.commerce.backend.modules.user.infra.entity.User;

@Data
public class OnPasswordForgotRequestEvent extends ApplicationEvent {
    private User user;
    private String token;

    public OnPasswordForgotRequestEvent(User user, String token) {
        super(user);
        this.user = user;
        this.token = token;
    }
}
