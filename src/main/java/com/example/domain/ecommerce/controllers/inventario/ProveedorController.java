package com.example.domain.ecommerce.controllers.inventario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.domain.ecommerce.dto.ProveedorDTO;
import com.example.domain.ecommerce.services.ProveedorService;

import lombok.extern.slf4j.Slf4j;


@Controller
@Slf4j
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    @GetMapping("/proveedores")
    public String proveedores(Model model) {
        model.addAttribute("proveedores", proveedorService.obtenerProveedores());
        return "proveedores";
    }

    @PostMapping("/agregar_prov")
    public String agregarProv(
            @ModelAttribute ProveedorDTO proveedorDTO,
            Model model) {

        proveedorService.createProv(proveedorDTO);

        model.addAttribute("proveedores", proveedorService.obtenerProveedores());

        return "redirect:/proveedores";
    }

    @PostMapping("/actualizar_prov/{id}")
    public String actualizarProv(
            @ModelAttribute ProveedorDTO proveedorDTO,
            @PathVariable int id,
            Model model) {

        proveedorService.updateProv(proveedorDTO, id);

        model.addAttribute("proveedores", proveedorService.obtenerProveedores());

        return "redirect:/proveedores";
    }

    @PostMapping("/eliminar_prov/{id}")
    public String eliminarProv(
            @PathVariable int id_proveedor,
            Model model) {

        proveedorService.eliminarProveedor(id_proveedor);

        model.addAttribute("proveedores", proveedorService.obtenerProveedores());

        return "redirect:/proveedores";

    }
}