package com.example.domain.ecommerce.controllers.inventario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.domain.ecommerce.dto.UserDTO;
import com.example.domain.ecommerce.services.UsuarioService;

import lombok.extern.slf4j.Slf4j;


@Controller
@Slf4j
public class UsuarioInventarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/usuarios")
    public String usuarios(Model model) {
        model.addAttribute("usuarios", usuarioService.listarUsuario());
        model.addAttribute("roles", usuarioService.listarRoles());
        return "venta/usuarios";
    }

    @PostMapping("/agregar_usu")
    public String agregarUsu(
            @ModelAttribute UserDTO userDTO,
            Model model) {

        usuarioService.createUser(userDTO);

        model.addAttribute("usuarios", usuarioService.listarUsuario());
        model.addAttribute("roles", usuarioService.listarRoles());

        return "redirect:/usuarios";
    }

    @PostMapping("/actualizar_usu/{id}")
    public String actualizarUsu(
            @ModelAttribute UserDTO userDTO,
            @PathVariable int id,
            Model model) {

        usuarioService.actualizarUsuarios(userDTO, id);

        model.addAttribute("usuarios", usuarioService.listarUsuario());
        model.addAttribute("roles", usuarioService.listarRoles());

        return "redirect:/usuarios";
    }

    @PostMapping("/eliminarUsuario/{id}")
    public String eliminarUsu(
            @PathVariable int id,
            Model model) {

        usuarioService.eliminarUsuario(id);

        model.addAttribute("usuarios", usuarioService.listarUsuario());
        model.addAttribute("roles", usuarioService.listarRoles());

        return "redirect:/usuarios";

    }
}
