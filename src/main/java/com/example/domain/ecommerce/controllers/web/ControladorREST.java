/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.domain.ecommerce.controllers.web;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;


@RequestMapping("/targus/principal")
@SessionAttributes({ "carrito"})
@Controller
@Slf4j
public class ControladorREST {

    int contador = 0;

    @GetMapping("/")
    public String comienzo(Model model) {
        return "commerce/principal";
    }

    @RequestMapping("/iniciar_crear")
    public String abrirIniciar() {
        return "commerce/iniciosesion";
    }

     @RequestMapping("/form_crear")
     public String abrirCrear(Model model) {
         return "commerce/crear_cuenta";
     }

     @RequestMapping("/carro")
     public String abrirCarrito(Model model) {
         return "commerce/carrito";
     }

     @RequestMapping("/opcion")
     public String abrirOpcion(Model model) {
         return "commerce/opcion_Usu";
     }

     @RequestMapping("/soporte")
     public String abrirSoporte(Model model) {
         return "commerce/soporte_Usu";
     }

     @RequestMapping("/cerrar")
     public String cerrar_sesion(HttpSession request, SessionStatus status) {
         status.setComplete();
         request.removeAttribute("user");
        return "redirect:/targus/iniciar_crear";
     }

     

}
