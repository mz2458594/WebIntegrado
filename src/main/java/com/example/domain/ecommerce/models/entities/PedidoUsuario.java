package com.example.domain.ecommerce.models.entities;

import java.math.BigDecimal;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pedidos_usuarios")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoUsuario extends Pedido {
    private BigDecimal envio;
}
