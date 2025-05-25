package com.example.domain.ecommerce.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.domain.ecommerce.dto.EmailDTO;
import com.example.domain.ecommerce.services.EmailService;

@RequestMapping("/api/mail")
@RestController
public class EmailController {  

    @Autowired
    private EmailService emailService;
    
    @PostMapping("/sendRegistrer")
    public ResponseEntity<String> sendEmailRegistrar(@RequestBody EmailDTO emailDTO){
        emailService.sendEmailRegistrar(emailDTO);
        return ResponseEntity.ok("Email de registro enviado");
    }

    @PostMapping("/sendRecover")
    public ResponseEntity<String>  sendEmailPassword (@RequestBody EmailDTO emailDTO){
        emailService.sendEmailPassword(emailDTO);
        return ResponseEntity.ok("Email de passoword enviado");
    }

}
