package com.example.domain.ecommerce.models.entities;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "ventas_inventario")
public class VentaInventario extends Venta {
    // BigDecimal efectivo;
    // BigDecimal vuelto;
}
