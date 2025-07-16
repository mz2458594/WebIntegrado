package com.example.domain.ecommerce.models.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "pedidos_proveedores")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoProveedor extends Pedido{

    @OneToOne(mappedBy = "pedidoProveedor", cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Comprobante comprobante;

}
