package com.example.domain.ecommerce.controllers.web;


import com.example.domain.ecommerce.dto.ProductDTO;
import com.example.domain.ecommerce.services.ProductoService;

import lombok.extern.slf4j.Slf4j;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@SessionAttributes({ "carrito" })
@Controller
@Slf4j
public class ProductoController {
    @Autowired
    private ProductoService productosService;


    @GetMapping("/")
    public String comienzo(Model model) {
        return "commerce/principal";
    }

    @GetMapping("/producto")
    public String abrir(Model model) {
        model.addAttribute("productos", productosService.listarProducto());
        model.addAttribute("categorias", productosService.obtenerCategorias());
        return "commerce/productos";
    }
    

}
