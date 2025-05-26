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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "control_comprobantes")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ControlComprobante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String serie;

    private int ultimoNumero;

    @Enumerated(EnumType.STRING)
    private TipoComprobante tipo;

    private LocalDateTime actualizacion;

}
