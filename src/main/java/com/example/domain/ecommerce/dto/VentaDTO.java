package com.example.domain.ecommerce.dto;

import java.sql.Date;
import lombok.Data;

@Data
public class VentaDTO {
    private Date fechaInicio;
    private Date fechaFinal;
    private String comprobante;
    private Integer idResponsable;
    private String tipoVenta;
}
