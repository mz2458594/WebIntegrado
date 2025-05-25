package com.example.domain.ecommerce.services;

import java.util.*;

import com.example.domain.ecommerce.dto.EmailDTO;
import com.example.domain.ecommerce.models.entities.Detalle_venta;
import com.example.domain.ecommerce.models.entities.Email;
import com.example.domain.ecommerce.models.entities.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    TemplateEngine templateEngine;

    @Value("${mail.urlFront}")
    private String urlFront;

    @Value("${mail.urlF}")
    private String urlF;

    public void sendEmailPassword(EmailDTO emailDTO) {

        Email correo = new Email("mz2458594@gmail.com", emailDTO.getMailTo(), "Recuperar Contraseña", emailDTO.getUserName(),
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c");

        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            Context context = new Context();
            Map<String, Object> model = new HashMap<>();

            model.put("username", correo.getUserName());
            model.put("url", urlFront + "/" + emailDTO.getId());

            context.setVariables(model);

            String htmlText = templateEngine.process("commerce/email_password", context);
            helper.setFrom(correo.getMailFrom());
            helper.setTo(correo.getMailTo());
            helper.setSubject(correo.getSubject());
            helper.setText(htmlText, true);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void sendEmailRegistrar(EmailDTO emailDTO) {

        Email correo = new Email("mz2458594@gmail.com", emailDTO.getMailTo(),
                "Registrar cuenta",
                emailDTO.getUserName(),
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c");

        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            Context context = new Context();
            Map<String, Object> model = new HashMap<>();

            model.put("username", correo.getUserName());
            model.put("url", urlF + "/" + emailDTO.getId());

            context.setVariables(model);

            String htmlText = templateEngine.process("commerce/email_registrar", context);
            helper.setFrom(correo.getMailFrom());
            helper.setTo(correo.getMailTo());
            helper.setSubject(correo.getSubject());
            helper.setText(htmlText, true);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}