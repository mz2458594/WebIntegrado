package com.example.domain.ecommerce.controllers.inventario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.domain.ecommerce.dto.ProductDTO;
import com.example.domain.ecommerce.services.CategoriaService;
import com.example.domain.ecommerce.services.ProductoService;
import com.example.domain.ecommerce.services.ProveedorService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@SessionAttributes({ "nombre", "id", "rol" })
public class ProductoInventarioController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ProveedorService proveedorService;

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/productos")
    public String productos(Model model) {
        model.addAttribute("productos", productoService.listarProducto());
        model.addAttribute("categorias", categoriaService.obtenerCategorias());
        model.addAttribute("proveedores", proveedorService.obtenerProveedores());
        return "venta/productos";
    }

    @PostMapping("/agregar_prod")
    public String agregarProd(
            @ModelAttribute ProductDTO productDTO,
            Model model) {
        ;
        productoService.agregarProducto(productDTO);
        model.addAttribute("productos", productoService.listarProducto());
        model.addAttribute("categorias", productoService.obtenerCategorias());
        return "redirect:/productos";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizarProd(
            @ModelAttribute ProductDTO productDTO, 
            @PathVariable int id,
            Model model) {

        productoService.actualizarProducto(productDTO, id);

        model.addAttribute("productos", productoService.listarProducto());
        model.addAttribute("categorias", productoService.obtenerCategorias());
        return "redirect:/productos";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarProd(
            @PathVariable int id,
            Model model) {

        productoService.eliminarProducto(id);

        
        model.addAttribute("productos", productoService.listarProducto());
        model.addAttribute("categorias", productoService.obtenerCategorias());
        return "redirect:/productos";

    }
}