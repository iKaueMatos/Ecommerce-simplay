package com.commerce.backend.auth.application.useCases.controller;

import com.commerce.backend.auth.application.services.ITokenService;
import com.commerce.backend.core.controller.PublicApiController;
import com.commerce.backend.user.application.useCases.dto.PasswordForgotConfirmRequest;
import com.commerce.backend.user.application.useCases.dto.PasswordForgotRequest;
import com.commerce.backend.user.application.useCases.dto.PasswordForgotValidateRequest;
import com.commerce.backend.user.application.useCases.dto.RegisterUserRequest;
import com.commerce.backend.user.application.useCases.dto.ValidateEmailRequest;
import com.commerce.backend.user.application.useCases.service.IUserService;
import com.commerce.backend.user.infra.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class PublicUserController extends PublicApiController {
    private final IUserService userService;
    private final ITokenService tokenService;

    @Autowired
    public PublicUserController(IUserService userService, ITokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }
    
    @PostMapping(value = "/account/registration")
    public ResponseEntity<HttpStatus> registerUser(@RequestBody @Valid RegisterUserRequest registerUserRequest) {
        User user = userService.register(registerUserRequest);
        tokenService.createEmailConfirmToken(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @GetMapping("/account/registrationConfirm")
    public ResponseEntity<HttpStatus> registrationTokenValidate(@RequestParam("token") String token) {
        boolean registrationConfirmed = tokenService.validateEmail(token);
        if (registrationConfirmed) return new ResponseEntity<>(HttpStatus.ACCEPTED);
        return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
    }

    @PostMapping(value = "/account/registration/validate")
    public ResponseEntity<HttpStatus> validateEmail(@RequestBody @Valid ValidateEmailRequest validateEmailRequest) {
        tokenService.validateEmail(validateEmailRequest.getToken());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/account/password/forgot")
    public ResponseEntity<HttpStatus> forgotPasswordRequest(@RequestBody @Valid PasswordForgotRequest passwordForgotRequest) {
        tokenService.createPasswordResetToken(passwordForgotRequest.getEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/account/password/forgot/validate")
    public ResponseEntity<HttpStatus> validateForgotPassword(@RequestBody @Valid PasswordForgotValidateRequest passwordForgotValidateRequest) {
        tokenService.validateForgotPassword(passwordForgotValidateRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/account/password/forgot/confirm")
    public ResponseEntity<HttpStatus> confirmForgotPassword(@RequestBody @Valid PasswordForgotConfirmRequest passwordForgotConfirmRequest) {
        tokenService.validateForgotPasswordConfirm(passwordForgotConfirmRequest.getToken());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
