package com.example.domain.ecommerce.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.domain.ecommerce.dto.LoginDTO;
import com.example.domain.ecommerce.dto.UserDTO;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    
    // @PostMapping("/registrer")
    // public ResponseEntity<TokenResponse> registrer (@RequestBody UserDTO loginDTO){

    // }


    // @PostMapping("/login")
    // public ResponseEntity<TokenResponse> login(@RequestBody LoginDTO loginDTO){

    // }

    // @PostMapping("/refresh")
    // public ResponseEntity<TokenResponse> refresh(@RequestHeader(HttpHeaders.AUTHORIZATION)){
        
    // }

}
