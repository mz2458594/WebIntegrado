package com.example.domain.ecommerce.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.domain.ecommerce.dto.ProveedorDTO;
import com.example.domain.ecommerce.models.entities.Proveedor;
import com.example.domain.ecommerce.services.ProveedorService;

@RequestMapping("/api/proveedor")
@RestController
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    @GetMapping("/")
    public ResponseEntity<List<Proveedor>> getProveedores() {
        return ResponseEntity.ok(proveedorService.obtenerProveedores());
    }

    @PostMapping("/create")
    public ResponseEntity<Proveedor> createProveedor(@RequestBody ProveedorDTO proveedorDTO) {
        Proveedor proveedor = proveedorService.createProv(proveedorDTO);
        return ResponseEntity.status(201).body(proveedor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Proveedor> updateProveedor(@RequestBody ProveedorDTO proveedorDTO, @PathVariable int id) {
        try {
            Proveedor proveedor = proveedorService.updateProv(proveedorDTO, id);
            return ResponseEntity.ok(proveedor);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProveedor(@PathVariable int id) {
        proveedorService.eliminarProveedor(id);
        return ResponseEntity.noContent().build();
    }

}
