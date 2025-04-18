package com.example.domain.ecommerce.controllers.web;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.domain.ecommerce.models.entities.Usuario;

import jakarta.servlet.http.HttpSession;

@ControllerAdvice
public class GlobalModelAtributte {
    
    @ModelAttribute
    public void addUserModel(HttpSession session, Model model){
        Usuario user = (Usuario) session.getAttribute("user");
        Usuario empleado = (Usuario) session.getAttribute("empleado");
        if (user != null) {
            model.addAttribute("user", user);
        }

        if (empleado != null) {
            model.addAttribute("empleado", empleado);
        }
    }

}
