package com.example.domain.ecommerce.controllers.inventario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.domain.ecommerce.dto.CategoriaDTO;
import com.example.domain.ecommerce.services.CategoriaService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/inventario/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;


    @GetMapping("/")
    public String listarCategorias(Model model) {
        model.addAttribute("categorias", categoriaService.obtenerCategorias());
        return "venta/categoria";
    }

    @PostMapping("/agregar_cat")
    public String agregarCategoria(@ModelAttribute CategoriaDTO categoriaDTO,
            Model model) {
        categoriaService.createCategory(categoriaDTO);
        model.addAttribute("categorias", categoriaService.obtenerCategorias());
        return "redirect:/inventario/categoria/";
    }

    @PostMapping("/actualizar_cat/{id}")
    public String actualizarCategoria(@ModelAttribute CategoriaDTO categoriaDTO,
    @PathVariable int id,
            Model model) {
        categoriaService.updateCategoria(categoriaDTO, id);
        model.addAttribute("categorias", categoriaService.obtenerCategorias());
        return "redirect:/inventario/categoria/";
    }

    @PostMapping("/eliminar_cat/{id}")
    public String eliminarCategoria(@PathVariable int id,
            Model model) {
        categoriaService.eliminarCategoria(id);
        model.addAttribute("categorias", categoriaService.obtenerCategorias());
        return "redirect:/inventario/categoria/";
    }
}