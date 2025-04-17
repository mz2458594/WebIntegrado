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
        model.addAttribute("productos", productosService.listarProducto());
        model.addAttribute("categorias", productosService.obtenerCategorias());
        return "commerce/productos";
    }

    @PostMapping("/insertar")
    public String insertar(
            @ModelAttribute ProductDTO productDTO,
            Model model) {



        // Llamar al repositorio para insertar los datos en la base de datos
        productosService.agregarProducto(productDTO);

        // Añadir un mensaje de éxito para mostrar en la página de confirmación
        model.addAttribute("mensaje", "Datos insertados con éxito");

        model.addAttribute("categorias", productosService.obtenerCategorias());
        model.addAttribute("productos", productosService.listarProducto());
        // Redirigir a una vista de confirmación
        return "redirect:commerce/adminProducto";
    }

    @PostMapping("/actualizar_prod/{id}")
    public String actualizarProductos(@ModelAttribute ProductDTO productoDTO, @PathVariable int id,
            Model model) {
        productosService.actualizarProducto( productoDTO, id);
        model.addAttribute("mensaje", "Datos actualizados con éxito");
        model.addAttribute("categorias", productosService.obtenerCategorias());
        model.addAttribute("productos", productosService.listarProducto());
        return "redirect:commerce/adminProducto";
    }


    @PostMapping("/eliminar_pro")
    public String eliminarProducto(
            @RequestParam("id_pr") int id_producto,
            Model model) {

        productosService.eliminarProducto(id_producto);
        model.addAttribute("mensaje", "Datos eliminados con éxito");

        model.addAttribute("categorias", productosService.obtenerCategorias());
        model.addAttribute("productos", productosService.listarProducto());
        return "redirect:commerce/adminProducto";
    }


    @GetMapping("/adminProducto")
    public String abrirAdmin(Model model) {
        model.addAttribute("productos", productosService.listarProducto());
        return "commerce/adminProducto"; 
    }


    @PostMapping("/buscar")
    public String buscar(Model model, @RequestParam("q") String buscar){
        model.addAttribute("buscar", buscar);
        model.addAttribute("categorias", productosService.obtenerCategorias());
        model.addAttribute("productos", productosService.listarProducto());
        return "commerce/productos";
    }

    

}
