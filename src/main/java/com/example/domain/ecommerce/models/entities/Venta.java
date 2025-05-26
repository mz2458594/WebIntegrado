package com.example.domain.ecommerce.models.entities;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ventas")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Venta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ventas")
    private int idVentas;

    @Column(name = "f_venta")
    private Timestamp fechaVenta;

    @Column(name = "total_venta")
    private double total;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL)
    private List<Detalle_venta> ventaProductos = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Usuario usuario;

    @OneToOne(mappedBy = "venta", cascade = CascadeType.ALL)
    private Comprobante comprobante;

}
