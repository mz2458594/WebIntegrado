package com.example.domain.ecommerce.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.domain.ecommerce.services.ControlComprobanteService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/control")
public class ControlComprobanteController {

    @Autowired
    private ControlComprobanteService controlComprobanteService;

    @GetMapping("/transaccion")
    public ResponseEntity<String> getTransaccion(@RequestParam String tipo) {
        return ResponseEntity.ok(controlComprobanteService.getNumeroTransaccion(tipo));
    }
}
