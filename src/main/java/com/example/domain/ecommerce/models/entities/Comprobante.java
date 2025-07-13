package com.example.domain.ecommerce.models.entities;

import java.time.LocalDateTime;
import com.example.domain.ecommerce.models.enums.TipoComprobante;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "comprobantes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comprobante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String numero;

    private LocalDateTime fechaEmision;

    @Enumerated(EnumType.STRING)
    private TipoComprobante tipo;

    @OneToOne
    @JoinColumn(name = "venta_id", nullable = true)
    @JsonIgnoreProperties({"comprobante", "ventaProductos", "usuario"})
    private Venta venta;

    @OneToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "pedido_id", nullable = true)
    @JsonIgnoreProperties({"comprobante", "detallePedidos", "user"})
    private PedidoProveedor pedidoProveedor;

    @Column(nullable = true)
    private String rucCliente;

    @Column(nullable = true)
    private String razonSocial;

}
