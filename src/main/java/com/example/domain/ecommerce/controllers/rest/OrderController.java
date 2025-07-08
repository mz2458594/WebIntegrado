package com.example.domain.ecommerce.controllers.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.domain.ecommerce.dto.PedidoFilterDTO;
import com.example.domain.ecommerce.models.entities.Pedido;
import com.example.domain.ecommerce.models.entities.PedidoUsuario;
import com.example.domain.ecommerce.services.PedidoService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private PedidoService pedidoService;

    @GetMapping("/user/{id}")
    public ResponseEntity<PedidoUsuario> getOrderUser(@PathVariable("id") int id) {
        return ResponseEntity.ok(pedidoService.obtenerPedidoUsuarioPorId(id));
    }

    @PostMapping("/filter")
    public ResponseEntity<List<Pedido>> getOrderFilter(@RequestBody PedidoFilterDTO pedidoFilterDTO){
        return ResponseEntity.ok(pedidoService.obtenerPedidosConFiltro(pedidoFilterDTO));
    }
}
