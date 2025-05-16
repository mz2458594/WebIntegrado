package com.example.domain.ecommerce.models.entities;

import java.time.LocalDateTime;

import com.example.domain.ecommerce.models.enums.TipoComprobante;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "control_comprobantes")
public class ControlComprobante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String serie;

    private int ultimoNumero;

    @Enumerated(EnumType.STRING)
    private TipoComprobante tipo;

    private LocalDateTime actualizacion;

    public ControlComprobante() {
    }

    public ControlComprobante(Long id, String serie, int ultimoNumero, TipoComprobante tipo,
            LocalDateTime actualizacion) {
        this.id = id;
        this.serie = serie;
        this.ultimoNumero = ultimoNumero;
        this.tipo = tipo;
        this.actualizacion = actualizacion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public int getUltimoNumero() {
        return ultimoNumero;
    }

    public void setUltimoNumero(int ultimoNumero) {
        this.ultimoNumero = ultimoNumero;
    }

    public TipoComprobante getTipo() {
        return tipo;
    }

    public void setTipo(TipoComprobante tipo) {
        this.tipo = tipo;
    }

    public LocalDateTime getActualizacion() {
        return actualizacion;
    }

    public void setActualizacion(LocalDateTime actualizacion) {
        this.actualizacion = actualizacion;
    }

}
