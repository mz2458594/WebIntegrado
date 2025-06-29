package com.example.domain.ecommerce.dto;

import java.sql.Date;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class VentaDTO {
    private Date fechaInicio;
    private Date fechaFinal;
}
