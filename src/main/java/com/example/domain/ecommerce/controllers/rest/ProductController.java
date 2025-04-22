package com.example.domain.ecommerce.controllers.rest;

import com.example.domain.ecommerce.dto.ProductDTO;
import com.example.domain.ecommerce.dto.UserDTO;
import com.example.domain.ecommerce.models.entities.Producto;
import com.example.domain.ecommerce.models.entities.Usuario;
import com.example.domain.ecommerce.services.ProductoService;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/products")
@RestController
@Slf4j
public class ProductController {
    @Autowired
    private ProductoService productosService;

    @GetMapping("/")
    public List<Producto> getAllProducts() {
        List<Producto> productos = productosService.listarProducto();
        return productos;
    }

    @PostMapping("/createProduct")
    public ResponseEntity<Producto> createProduct(ProductDTO productDTO) {
        Producto producto = productosService.agregarProducto(productDTO);

        return new ResponseEntity<>(producto, HttpStatus.OK);
    }

    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id) {
        productosService.eliminarProducto(id);
        return new ResponseEntity<>("Producto eliminado con éxito", HttpStatus.OK);
    }

    @PutMapping("/updateProduct/{id}")
    public ResponseEntity<?> updateProduct(@RequestBody ProductDTO productDTO, @PathVariable int id) {

        productosService.actualizarProducto(productDTO, id);

        return ResponseEntity.ok("Producto actualizado correctamente");

    }

}
