package com.commerce.backend.service;

import com.commerce.backend.modules.auth.domain.event.OnPasswordForgotRequestEvent;
import com.commerce.backend.modules.auth.domain.event.OnRegistrationCompleteEvent;
import com.commerce.backend.modules.auth.domain.service.TokenServiceImpl;
import com.commerce.backend.modules.auth.infra.entity.PasswordForgotToken;
import com.commerce.backend.modules.auth.infra.entity.VerificationToken;
import com.commerce.backend.modules.auth.infra.repository.PasswordForgotTokenRepository;
import com.commerce.backend.modules.auth.infra.repository.VerificationTokenRepository;
import com.commerce.backend.core.error.exception.InvalidArgumentException;
import com.commerce.backend.core.error.exception.ResourceNotFoundException;
import com.commerce.backend.modules.user.application.useCases.dto.PasswordForgotValidateRequest;
import com.commerce.backend.modules.user.application.useCases.service.IUserService;
import com.commerce.backend.modules.user.infra.entity.User;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class TokenServiceImplTest {

    @InjectMocks
    private TokenServiceImpl tokenService;

    @Mock
    private IUserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @Mock
    private VerificationTokenRepository verificationTokenRepository;

    @Mock
    private PasswordForgotTokenRepository passwordForgotTokenRepository;

    private User user;
    private Faker faker;

    @BeforeEach
    public void setUp() {
        user = new User();
        faker = new Faker();
    }

    @Test
    void it_should_create_email_confirm_token() {
        ArgumentCaptor<VerificationToken> verificationTokenArgumentCaptor = ArgumentCaptor.forClass(VerificationToken.class);
        ArgumentCaptor<OnRegistrationCompleteEvent> onRegistrationCompleteEventArgumentCaptor = ArgumentCaptor.forClass(OnRegistrationCompleteEvent.class);

        given(verificationTokenRepository.save(any(VerificationToken.class))).willReturn(new VerificationToken());

        tokenService.createEmailConfirmToken(user);
        verify(verificationTokenRepository).save(verificationTokenArgumentCaptor.capture());
        verify(eventPublisher).publishEvent(onRegistrationCompleteEventArgumentCaptor.capture());
        then(user).isEqualTo(onRegistrationCompleteEventArgumentCaptor.getValue().getUser());
    
        then(verificationTokenArgumentCaptor.getValue().getToken()).isEqualTo(onRegistrationCompleteEventArgumentCaptor.getValue().getToken());
    }

    @Test
    void it_should_create_password_reset_token_if_it_does_not_exist() {
        String email = faker.lorem().word();
        ArgumentCaptor<PasswordForgotToken> passwordForgotTokenArgumentCaptor = ArgumentCaptor.forClass(PasswordForgotToken.class);
        ArgumentCaptor<OnPasswordForgotRequestEvent> onPasswordForgotRequestEventArgumentCaptor = ArgumentCaptor.forClass(OnPasswordForgotRequestEvent.class);

        given(userService.findByEmail(email)).willReturn(user);
        given(passwordForgotTokenRepository.findByUser(user)).willReturn(Optional.empty());
        given(passwordForgotTokenRepository.save(any(PasswordForgotToken.class))).willReturn(new PasswordForgotToken());

        tokenService.createPasswordResetToken(email);

        verify(passwordForgotTokenRepository).save(passwordForgotTokenArgumentCaptor.capture());
        verify(eventPublisher).publishEvent(onPasswordForgotRequestEventArgumentCaptor.capture());


        then(user).isEqualTo(onPasswordForgotRequestEventArgumentCaptor.getValue().getUser());
        then(onPasswordForgotRequestEventArgumentCaptor.getValue().getToken()).isEqualTo(onPasswordForgotRequestEventArgumentCaptor.getValue().getToken());

    }

    @Test
    void it_should_create_password_reset_token_if_it_already_exists() {
        String email = faker.lorem().word();
        ArgumentCaptor<PasswordForgotToken> passwordForgotTokenArgumentCaptor = ArgumentCaptor.forClass(PasswordForgotToken.class);
        ArgumentCaptor<OnPasswordForgotRequestEvent> onPasswordForgotRequestEventArgumentCaptor = ArgumentCaptor.forClass(OnPasswordForgotRequestEvent.class);

        given(userService.findByEmail(email)).willReturn(user);
        given(passwordForgotTokenRepository.findByUser(user)).willReturn(Optional.of(new PasswordForgotToken()));
        given(passwordForgotTokenRepository.save(any(PasswordForgotToken.class))).willReturn(new PasswordForgotToken());

        tokenService.createPasswordResetToken(email);
        verify(passwordForgotTokenRepository).save(passwordForgotTokenArgumentCaptor.capture());
        verify(eventPublisher).publishEvent(onPasswordForgotRequestEventArgumentCaptor.capture());

        then(user).isEqualTo(onPasswordForgotRequestEventArgumentCaptor.getValue().getUser());
        then(onPasswordForgotRequestEventArgumentCaptor.getValue().getToken()).isEqualTo(onPasswordForgotRequestEventArgumentCaptor.getValue().getToken());
    }

    @Test
    void it_should_validate_email_by_token() {
        String token = faker.random().hex();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setUser(user);
        verificationToken.setExpiryDate(Date.from(Instant.now().plus(Duration.ofHours(faker.number().randomDigitNotZero()))));

        ArgumentCaptor<VerificationToken> verificationTokenArgumentCaptor = ArgumentCaptor.forClass(VerificationToken.class);
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

        given(verificationTokenRepository.findByToken(token)).willReturn(Optional.of(verificationToken));
        given(userService.saveUser(user)).willReturn(user);

        tokenService.validateEmail(token);

        verify(verificationTokenRepository).delete(verificationTokenArgumentCaptor.capture());
        verify(userService).saveUser(userArgumentCaptor.capture());

        then(verificationTokenArgumentCaptor.getValue().getToken()).isEqualTo(verificationToken.getToken());
        then(verificationTokenArgumentCaptor.getValue().getUser()).isEqualTo(verificationToken.getUser());
        then(verificationTokenArgumentCaptor.getValue().getUser().getEmailVerified()).isEqualTo(1);
    }

    @Test
    void it_should_throw_exception_when_no_token_on_validate_email() {
        String token = faker.random().hex();

        given(verificationTokenRepository.findByToken(token)).willReturn(Optional.empty());
        assertThatThrownBy(() -> tokenService.validateEmail(token))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Null verification token");
    }

    @Test
    void it_should_throw_exception_when_no_user_on_validate_email() {

        String token = faker.random().hex();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setUser(null);
        verificationToken.setExpiryDate(Date.from(Instant.now().plus(Duration.ofHours(faker.number().randomDigitNotZero()))));

        given(verificationTokenRepository.findByToken(token)).willReturn(Optional.of(verificationToken));
        assertThatThrownBy(() -> tokenService.validateEmail(token))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("User not found");
    }

    @Test
    void it_should_throw_exception_when_token_expired_on_validate_email() {

        String token = faker.random().hex();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setUser(user);
        verificationToken.setExpiryDate(Date.from(Instant.now().minus(Duration.ofHours(faker.number().randomDigitNotZero()))));

        given(verificationTokenRepository.findByToken(token)).willReturn(Optional.of(verificationToken));

        assertThatThrownBy(() -> tokenService.validateEmail(token))
                .isInstanceOf(InvalidArgumentException.class)
                .hasMessage("Token is expired");
    }

    @Test
    void it_should_validate_forgot_password_confirm() {
        String token = faker.random().hex();
        PasswordForgotToken passwordForgotToken = new PasswordForgotToken();
        passwordForgotToken.setUser(user);
        passwordForgotToken.setExpiryDate(Date.from(Instant.now().plus(Duration.ofHours(faker.number().randomDigitNotZero()))));
        given(passwordForgotTokenRepository.findByToken(token)).willReturn(Optional.of(passwordForgotToken));
        tokenService.validateForgotPasswordConfirm(token);

        verify(passwordForgotTokenRepository).findByToken(token);
    }

    @Test
    void it_should_throw_exception_when_no_token_on_validate_forgot_password_confirm() {
        String token = faker.random().hex();
        given(passwordForgotTokenRepository.findByToken(token)).willReturn(Optional.empty());

        assertThatThrownBy(() -> tokenService.validateForgotPasswordConfirm(token))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Token not found");
    }

    @Test
    void it_should_throw_exception_when_token_expired_on_validate_forgot_password_confirm() {
        String token = faker.random().hex();
        PasswordForgotToken passwordForgotToken = new PasswordForgotToken();
        passwordForgotToken.setUser(user);
        passwordForgotToken.setExpiryDate(Date.from(Instant.now().minus(Duration.ofHours(faker.number().randomDigitNotZero()))));

        given(passwordForgotTokenRepository.findByToken(token)).willReturn(Optional.of(passwordForgotToken));
        assertThatThrownBy(() -> tokenService.validateForgotPasswordConfirm(token))
                .isInstanceOf(InvalidArgumentException.class)
                .hasMessage("Token is expired");
    }

    @Test
    void it_should_validate_forgot_password() {
        String token = faker.random().hex();

        PasswordForgotValidateRequest passwordForgotValidateRequest = new PasswordForgotValidateRequest();
        passwordForgotValidateRequest.setToken(token);
        passwordForgotValidateRequest.setNewPassword(faker.lorem().word());
        passwordForgotValidateRequest.setNewPasswordConfirm(faker.lorem().word());

        PasswordForgotToken passwordForgotToken = new PasswordForgotToken();
        passwordForgotToken.setUser(user);
        passwordForgotToken.setExpiryDate(Date.from(Instant.now().plus(Duration.ofHours(faker.number().randomDigitNotZero()))));

        ArgumentCaptor<PasswordForgotToken> passwordForgotTokenArgumentCaptor = ArgumentCaptor.forClass(PasswordForgotToken.class);
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

        given(passwordForgotTokenRepository.findByToken(passwordForgotValidateRequest.getToken())).willReturn(Optional.of(passwordForgotToken));
        given(passwordEncoder.matches(passwordForgotValidateRequest.getNewPassword(), user.getPassword())).willReturn(false);
        given(passwordEncoder.encode(any())).willReturn(passwordForgotValidateRequest.getNewPassword());
        given(userService.saveUser(user)).willReturn(user);

        tokenService.validateForgotPassword(passwordForgotValidateRequest);

        verify(passwordForgotTokenRepository).delete(passwordForgotTokenArgumentCaptor.capture());
        verify(userService).saveUser(userArgumentCaptor.capture());

        then(passwordForgotTokenArgumentCaptor.getValue().getToken()).isEqualTo(passwordForgotToken.getToken());
        then(passwordForgotTokenArgumentCaptor.getValue().getUser()).isEqualTo(passwordForgotToken.getUser());

    }

    @Test
    void it_should_throw_exception_when_no_token_on_validate_forgot_password() {

        String token = faker.random().hex();
        PasswordForgotValidateRequest passwordForgotValidateRequest = new PasswordForgotValidateRequest();
        passwordForgotValidateRequest.setToken(token);
        passwordForgotValidateRequest.setNewPassword(faker.lorem().word());
        passwordForgotValidateRequest.setNewPasswordConfirm(faker.lorem().word());

        given(passwordForgotTokenRepository.findByToken(token)).willReturn(Optional.empty());
        assertThatThrownBy(() -> tokenService.validateForgotPassword(passwordForgotValidateRequest))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Token not found");
    }

    @Test
    void it_should_throw_exception_when_no_user_on_validate_forgot_password() {

        // given
        String token = faker.random().hex();
        PasswordForgotValidateRequest passwordForgotValidateRequest = new PasswordForgotValidateRequest();
        passwordForgotValidateRequest.setToken(token);
        passwordForgotValidateRequest.setNewPassword(faker.lorem().word());
        passwordForgotValidateRequest.setNewPasswordConfirm(faker.lorem().word());

        PasswordForgotToken passwordForgotToken = new PasswordForgotToken();
        passwordForgotToken.setUser(null);
        passwordForgotToken.setExpiryDate(Date.from(Instant.now().plus(Duration.ofHours(faker.number().randomDigitNotZero()))));

        given(passwordForgotTokenRepository.findByToken(token)).willReturn(Optional.of(passwordForgotToken));

        assertThatThrownBy(() -> tokenService.validateForgotPassword(passwordForgotValidateRequest))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("User not found");
    }

    @Test
    void it_should_throw_exception_when_token_expired_on_validate_forgot_password() {
        String token = faker.random().hex();
        PasswordForgotValidateRequest passwordForgotValidateRequest = new PasswordForgotValidateRequest();
        passwordForgotValidateRequest.setToken(token);
        passwordForgotValidateRequest.setNewPassword(faker.lorem().word());
        passwordForgotValidateRequest.setNewPasswordConfirm(faker.lorem().word());

        PasswordForgotToken passwordForgotToken = new PasswordForgotToken();
        passwordForgotToken.setUser(user);
        passwordForgotToken.setExpiryDate(Date.from(Instant.now().minus(Duration.ofHours(faker.number().randomDigitNotZero()))));

        given(passwordForgotTokenRepository.findByToken(token)).willReturn(Optional.of(passwordForgotToken));
        assertThatThrownBy(() -> tokenService.validateForgotPassword(passwordForgotValidateRequest))
                .isInstanceOf(InvalidArgumentException.class)
                .hasMessage("Token is expired");
    }
}