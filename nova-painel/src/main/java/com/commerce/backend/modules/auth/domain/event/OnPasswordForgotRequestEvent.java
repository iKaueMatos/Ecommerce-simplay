package com.commerce.backend.auth.domain.event;


import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import com.commerce.backend.user.infra.entity.User;

@Getter
@Setter
public class OnPasswordForgotRequestEvent extends ApplicationEvent {
    private User user;
    private String token;

    public OnPasswordForgotRequestEvent(User user, String token) {
        super(user);
        this.user = user;
        this.token = token;
    }
}
