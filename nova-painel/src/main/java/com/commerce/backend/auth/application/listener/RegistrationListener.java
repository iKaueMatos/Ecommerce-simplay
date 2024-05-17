package com.commerce.backend.auth.application.listener;

import com.commerce.backend.auth.domain.event.OnRegistrationCompleteEvent;
import com.commerce.backend.shared.constants.MailConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MailConstants mailConstants;

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        String recipientAddress = event.getUser().getEmail();
        String subject = "Confirmação de Registro na plataforma Nova";
        String confirmationUrl = mailConstants.getHostAddress() + "api/public/account/registrationConfirm?token=" + event.getToken();
        String message = "Olá,\n\nPor favor, confirme seu email clicando no link abaixo:\n";
    
        StringBuilder emailBody = new StringBuilder();
        emailBody.append(message);
        emailBody.append(confirmationUrl);
        emailBody.append("\n\nObrigado por se registrar na plataforma Nova.\n\nAtenciosamente,\nEquipe Nova");
    
        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom("novasoftwareorganization@gmail.com");
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(emailBody.toString());
    
        mailSender.send(email);
    }
}