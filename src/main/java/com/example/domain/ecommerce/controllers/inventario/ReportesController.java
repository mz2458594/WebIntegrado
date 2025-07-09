package com.example.domain.ecommerce.controllers.inventario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.domain.ecommerce.services.CategoriaService;
import com.example.domain.ecommerce.services.ProveedorService;
import com.example.domain.ecommerce.services.UsuarioService;
import com.example.domain.ecommerce.services.VentaService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/inventario/reportes")
public class ReportesController {

    @Autowired
    private VentaService ventaService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private ProveedorService proveedorService;


    @GetMapping("/ventas")
    public String verReportes(Model model,  HttpSession session){

        //  Empleado empleado = (Empleado) session.getAttribute("empleado");

        // if (empleado == null) {
        //     model.addAttribute("error", "No hay usuario logeado en el sistema");
        //     return "redirect:/inventario/principal/index";
        // }
        model.addAttribute("ventas", ventaService.getVentas());
        model.addAttribute("empleados", usuarioService.getEmpleados());
        return "venta/reportes";
    }

    @GetMapping("/productos")
    public String verReportesInventario(Model model,  HttpSession session){
        model.addAttribute("proveedores", proveedorService.obtenerProveedores());
        model.addAttribute("categorias", categoriaService.obtenerCategorias());
        return "venta/reportesProductos";
    }


}
