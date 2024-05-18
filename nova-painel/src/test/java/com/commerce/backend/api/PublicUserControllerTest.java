package com.commerce.backend.api;

import com.commerce.backend.modules.auth.application.services.ITokenService;
import com.commerce.backend.modules.auth.application.useCases.controller.PublicUserController;
import com.commerce.backend.modules.user.application.useCases.dto.PasswordForgotConfirmRequest;
import com.commerce.backend.modules.user.application.useCases.dto.PasswordForgotRequest;
import com.commerce.backend.modules.user.application.useCases.dto.PasswordForgotValidateRequest;
import com.commerce.backend.modules.user.application.useCases.dto.RegisterUserRequest;
import com.commerce.backend.modules.user.application.useCases.dto.ValidateEmailRequest;
import com.commerce.backend.modules.user.application.useCases.service.IUserService;
import com.commerce.backend.modules.user.infra.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
@WebMvcTest(PublicUserController.class)
@AutoConfigureWebClient
@ComponentScan(basePackages = {"com.commerce.backend.modulesconstants"})
class PublicUserControllerTest {

    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    private IUserService userService;
    @MockBean
    private ITokenService tokenService;
    @Autowired
    private MockMvc mockMvc;
    private Faker faker;

    @BeforeEach
    public void setUp() {
        faker = new Faker();
    }

    @Test
    void it_should_register_user() throws Exception {

        // given
        String email = String.format("%s@%s.com", faker.lorem().characters(1, 10), faker.lorem().characters(1, 10));
        String password = faker.lorem().characters(6, 52);
        String passwordRepeat = password + "";

        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setEmail(email);
        registerUserRequest.setPassword(password);
        registerUserRequest.setPasswordRepeat(passwordRepeat);

        User user = new User();


        given(userService.register(registerUserRequest)).willReturn(user);

        // when
        mockMvc.perform(post("/api/public/account/registration")
                .content(objectMapper.writeValueAsString(registerUserRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();


        // then
        verify(userService, times(1)).register(registerUserRequest);
        verify(tokenService, times(1)).createEmailConfirmToken(user);
    }

    @Test
    void it_should_not_register_user_if_invalid_request() throws Exception {

        // given
        String email = String.valueOf(faker.number().randomDigitNotZero());
        String password = faker.lorem().characters(6, 52);
        String passwordRepeat = password + "";

        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setEmail(email);
        registerUserRequest.setPassword(password);
        registerUserRequest.setPasswordRepeat(passwordRepeat);

        User user = new User();


        given(userService.register(registerUserRequest)).willReturn(user);

        // when
        MvcResult result = mockMvc.perform(post("/api/public/account/registration")
                .content(objectMapper.writeValueAsString(registerUserRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn();


        // then
        verify(userService, times(0)).register(registerUserRequest);
        verify(tokenService, times(0)).createEmailConfirmToken(user);
        then(result.getResponse().getContentAsString()).contains("Invalid email");
    }

    @Test
    void it_should_validate_email() throws Exception {

        // given
        String token = faker.lorem().word();
        ValidateEmailRequest validateEmailRequest = new ValidateEmailRequest();
        validateEmailRequest.setToken(token);

        // when
        mockMvc.perform(post("/api/public/account/registration/validate")
                .content(objectMapper.writeValueAsString(validateEmailRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();


        // then
        verify(tokenService, times(1)).validateEmail(token);
    }

    @Test
    void it_should_not_validate_email_if_invalid_token() throws Exception {
        String token = "";
        ValidateEmailRequest validateEmailRequest = new ValidateEmailRequest();
        validateEmailRequest.setToken(token);

        MvcResult result = mockMvc.perform(post("/api/public/account/registration/validate")
                .content(objectMapper.writeValueAsString(validateEmailRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn();

        verify(tokenService, times(0)).validateEmail(token);
        then(result.getResponse().getContentAsString()).contains("must not be blank");
    }

    @Test
    void it_should_create_password_reset_token() throws Exception {
        String email = String.format("%s@%s.com", faker.lorem().characters(1, 10), faker.lorem().characters(1, 10));
        PasswordForgotRequest passwordForgotRequest = new PasswordForgotRequest();
        passwordForgotRequest.setEmail(email);

        mockMvc.perform(post("/api/public/account/password/forgot")
                .content(objectMapper.writeValueAsString(passwordForgotRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        verify(tokenService, times(1)).createPasswordResetToken(email);
    }

    @Test
    void it_should_not_create_password_reset_token_if_invalid_email() throws Exception {
        String email = String.valueOf(faker.number().randomDigitNotZero());
        PasswordForgotRequest passwordForgotRequest = new PasswordForgotRequest();
        passwordForgotRequest.setEmail(email);

        MvcResult result = mockMvc.perform(post("/api/public/account/password/forgot")
                .content(objectMapper.writeValueAsString(passwordForgotRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn();

        verify(tokenService, times(0)).createPasswordResetToken(email);
        then(result.getResponse().getContentAsString()).contains("Invalid email");
    }

    @Test
    void it_should_validate_forgot_password_request() throws Exception {
        String token = faker.lorem().word();
        String password = faker.lorem().characters(6, 52);
        String passwordRepeat = password + "";

        PasswordForgotValidateRequest passwordForgotValidateRequest = new PasswordForgotValidateRequest();
        passwordForgotValidateRequest.setToken(token);
        passwordForgotValidateRequest.setNewPassword(password);
        passwordForgotValidateRequest.setNewPasswordConfirm(passwordRepeat);

        // when
        mockMvc.perform(post("/api/public/account/password/forgot/validate")
                .content(objectMapper.writeValueAsString(passwordForgotValidateRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();


        // then
        verify(tokenService, times(1)).validateForgotPassword(passwordForgotValidateRequest);
    }

    @Test
    void it_should_not_validate_forgot_password_request_if_invalid_request() throws Exception {

        // given
        String token = null;
        String password = faker.lorem().characters(6, 52);
        String passwordRepeat = password + "";

        PasswordForgotValidateRequest passwordForgotValidateRequest = new PasswordForgotValidateRequest();
        passwordForgotValidateRequest.setToken(token);
        passwordForgotValidateRequest.setNewPassword(password);
        passwordForgotValidateRequest.setNewPasswordConfirm(passwordRepeat);

        // when
        MvcResult result = mockMvc.perform(post("/api/public/account/password/forgot/validate")
                .content(objectMapper.writeValueAsString(passwordForgotValidateRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn();


        // then
        verify(tokenService, times(0)).validateForgotPassword(passwordForgotValidateRequest);
        then(result.getResponse().getContentAsString()).contains("must not be blank");
    }

    @Test
    void it_should_confirm_forgot_password_request() throws Exception {
        String token = faker.lorem().word();
        PasswordForgotConfirmRequest passwordForgotConfirmRequest = new PasswordForgotConfirmRequest();
        passwordForgotConfirmRequest.setToken(token);

        mockMvc.perform(post("/api/public/account/password/forgot/confirm")
                .content(objectMapper.writeValueAsString(passwordForgotConfirmRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        verify(tokenService, times(1)).validateForgotPasswordConfirm(token);
    }

    @Test
    void it_should_not_confirm_forgot_password_request_if_invalid_request() throws Exception {
        String token = null;

        PasswordForgotConfirmRequest passwordForgotConfirmRequest = new PasswordForgotConfirmRequest();
        passwordForgotConfirmRequest.setToken(token);

        MvcResult result = mockMvc.perform(post("/api/public/account/password/forgot/confirm")
                .content(objectMapper.writeValueAsString(passwordForgotConfirmRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn();

        verify(tokenService, times(0)).validateForgotPasswordConfirm(token);
        then(result.getResponse().getContentAsString()).contains("must not be blank");
    }
}