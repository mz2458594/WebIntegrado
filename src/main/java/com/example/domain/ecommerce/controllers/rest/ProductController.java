package com.example.domain.ecommerce.controllers.rest;


import com.example.domain.ecommerce.models.entities.Producto;
import com.example.domain.ecommerce.services.ProductoService;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


// @RequestMapping("/api/products")
// @RestController
// @Slf4j
// public class ProductController {
//     @Autowired
//     private ProductoService productosService;

//     @GetMapping("/products")
//     public List<Producto> obtenerProductos(){
//         List<Producto> productos = productosService.listarProducto();
//         return productos;
//     }

// }
