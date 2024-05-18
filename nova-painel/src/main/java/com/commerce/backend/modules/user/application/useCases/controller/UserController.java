package com.commerce.backend.modules.user.application.useCases.controller;

import com.commerce.backend.core.controller.ApiController;
import com.commerce.backend.modules.user.application.useCases.dto.PasswordResetRequest;
import com.commerce.backend.modules.user.application.useCases.dto.UpdateUserAddressRequest;
import com.commerce.backend.modules.user.application.useCases.dto.UpdateUserRequest;
import com.commerce.backend.modules.user.application.useCases.dto.UserResponse;
import com.commerce.backend.modules.user.application.useCases.service.IUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
public class UserController extends ApiController {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/account")
    public ResponseEntity<UserResponse> getUser() {
        UserResponse userResponse = userService.fetchUser();
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @PutMapping(value = "/account")
    public ResponseEntity<UserResponse> updateUser(@RequestBody @Valid UpdateUserRequest updateUserRequest) {
        UserResponse userResponse = userService.updateUser(updateUserRequest);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @PutMapping(value = "/account/address")
    public ResponseEntity<UserResponse> updateUserAddress(@RequestBody @Valid UpdateUserAddressRequest updateUserAddressRequest) {
        UserResponse userResponse = userService.updateUserAddress(updateUserAddressRequest);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @PostMapping(value = "/account/password/reset")
    public ResponseEntity<HttpStatus> passwordReset(@RequestBody @Valid PasswordResetRequest passwordResetRequest) {
        userService.resetPassword(passwordResetRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/account/status")
    public ResponseEntity<Boolean> getVerificationStatus() {
        Boolean status = userService.getVerificationStatus();
        return new ResponseEntity<>(status, HttpStatus.OK);
    }
}
