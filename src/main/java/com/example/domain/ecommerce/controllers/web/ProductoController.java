package com.example.domain.ecommerce.controllers.web;


import com.example.domain.ecommerce.dto.ProductDTO;
import com.example.domain.ecommerce.services.ProductoService;

import lombok.extern.slf4j.Slf4j;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/targus/producto")
@SessionAttributes({ "carrito" })
@Controller
@Slf4j
public class ProductoController {
    @Autowired
    private ProductoService productosService;

    @GetMapping("/")
    public String abrir(Model model) {
        model.addAttribute("productos", productosService.listarProducto());
        model.addAttribute("categorias", productosService.obtenerCategorias());
        return "commerce/productos";
    }
    
    @GetMapping("/detalle/{id}")
    public String detalle(Model model, @PathVariable int id){
        model.addAttribute("producto", productosService.obtenerProductoPorId(id));
        return "commerce/producto-detalle";
    }

}
