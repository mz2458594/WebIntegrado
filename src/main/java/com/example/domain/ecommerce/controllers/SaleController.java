package com.example.domain.ecommerce.controllers;

import com.example.domain.ecommerce.dto.RequestDTO;
import com.example.domain.ecommerce.models.entities.Venta;
import com.example.domain.ecommerce.services.VentaService;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/sales")
public class SaleController {

    @Autowired
    VentaService ventasService;

    @GetMapping("/")
    public ResponseEntity<List<Venta>> getVentas() {
        return ResponseEntity.ok(ventasService.obtenerVentas());
    }

    @PostMapping("/createSale")
    public ResponseEntity<Venta> createSale(@RequestBody RequestDTO request) {
        Venta venta = ventasService.crearVenta(request);
        return ResponseEntity.status(201).body(venta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVenta(@PathVariable int id) {
        ventasService.deleteVenta(id);
        return ResponseEntity.noContent().build();
    }

}
