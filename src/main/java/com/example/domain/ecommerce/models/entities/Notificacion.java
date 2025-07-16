package com.example.domain.ecommerce.models.entities;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Notificacion {
    private String asunto;
    private String mensaje;
    private LocalDateTime fecha;
}
