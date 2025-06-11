package com.example.domain.ecommerce.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "detalles_pedidos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetallePedido {
    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_detalle;

    private int cantidad;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Producto producto;

    private double subtotal;

}
