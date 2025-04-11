package com.example.domain.ecommerce.services;
import java.util.*;

import com.example.domain.ecommerce.models.entities.Email;
import com.example.domain.ecommerce.models.entities.Producto;
import com.example.domain.ecommerce.models.entities.Venta_producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    // @Autowired
    // JavaMailSender javaMailSender;

    // @Autowired
    // TemplateEngine templateEngine;

    // @Value("${mail.urlFront}")
    // private String urlFront;

    // @Value("${mail.urlF}")
    // private String urlF;

    // public void sendEmail() {
    //     SimpleMailMessage message = new SimpleMailMessage();
    //     message.setFrom("mz2458594@gmail.com");
    //     message.setTo("mz2458594@gmail.com");
    //     message.setSubject("Prueba envio email simple");
    //     message.setText("Esto es el contenido del email");

    //     javaMailSender.send(message);
    // }

    // public void sendEmailTemplate(Email email, int id){
    //     MimeMessage message = javaMailSender.createMimeMessage();
    //     try {
    //         MimeMessageHelper helper = new MimeMessageHelper(message,true);
    //         Context context = new Context();
    //         Map<String, Object> model = new HashMap<>();

    //         model.put("username", email.getUserName());
    //         model.put("url", urlFront+"/"+id);

    //         context.setVariables(model);

    //         String htmlText = templateEngine.process("email_password", context);
    //         helper.setFrom(email.getMailFrom());
    //         helper.setTo(email.getMailTo());
    //         helper.setSubject(email.getSubject());
    //         helper.setText(htmlText, true);

    //         javaMailSender.send(message);
    //     } catch (MessagingException e) {
    //         e.printStackTrace();
    //     }
    // }

    // public void sendEmailVenta(Email email, List<Venta_producto> detalle_ventas, double total, List<Producto> productos){
    //     MimeMessage message = javaMailSender.createMimeMessage();
    //     try {
    //         MimeMessageHelper helper = new MimeMessageHelper(message,true);
    //         Context context = new Context();
    //         Map<String, Object> model = new HashMap<>();

    //         model.put("username", email.getUserName());
    //         model.put("url", urlFront);
    //         model.put("lista", detalle_ventas);
    //         model.put("total", total);
    //         model.put("productos", productos);


    //         context.setVariables(model);

    //         String htmlText = templateEngine.process("mail_detalle_pedido", context);
    //         helper.setFrom(email.getMailFrom());
    //         helper.setTo(email.getMailTo());
    //         helper.setSubject(email.getSubject());
    //         helper.setText(htmlText, true);

    //         javaMailSender.send(message);
    //     } catch (MessagingException e) {
    //         e.printStackTrace();
    //     }
    // }


    // public void sendEmailRegistrar(Email email, int id){
    //     MimeMessage message = javaMailSender.createMimeMessage();
    //     try {
    //         MimeMessageHelper helper = new MimeMessageHelper(message,true);
    //         Context context = new Context();
    //         Map<String, Object> model = new HashMap<>();

    //         model.put("username", email.getUserName());
    //         model.put("url", urlF+"/"+id);

    //         context.setVariables(model);

    //         String htmlText = templateEngine.process("email_registrar", context);
    //         helper.setFrom(email.getMailFrom());
    //         helper.setTo(email.getMailTo());
    //         helper.setSubject(email.getSubject());
    //         helper.setText(htmlText, true);

    //         javaMailSender.send(message);
    //     } catch (MessagingException e) {
    //         e.printStackTrace();
    //     }
    // }
}