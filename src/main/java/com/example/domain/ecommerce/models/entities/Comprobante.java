package com.example.domain.ecommerce.models.entities;

import java.time.LocalDateTime;

import com.example.domain.ecommerce.models.enums.TipoComprobante;

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

@Entity
@Table(name = "comprobantes")
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
    @JoinColumn(name = "venta_id", nullable = false)
    private Venta venta;

    @Column(nullable = true)
    private String rucCliente;

    @Column(nullable = true)
    private String razonSocial;

    public Comprobante() {
    }

    public Comprobante(int id, String numero, LocalDateTime fechaEmision, TipoComprobante tipo, Venta venta,
            String rucCliente, String razonSocial) {
        this.id = id;
        this.numero = numero;
        this.fechaEmision = fechaEmision;
        this.tipo = tipo;
        this.venta = venta;
        this.rucCliente = rucCliente;
        this.razonSocial = razonSocial;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public LocalDateTime getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDateTime fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public TipoComprobante getTipo() {
        return tipo;
    }

    public void setTipo(TipoComprobante tipo) {
        this.tipo = tipo;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public String getRucCliente() {
        return rucCliente;
    }

    public void setRucCliente(String rucCliente) {
        this.rucCliente = rucCliente;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    


}
