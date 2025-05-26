package com.example.domain.ecommerce.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String nombre;
    private String apellido;
    private String num_documento;
    private String celular;
    private String calle;
    private String ciudad;
    private String distrito;
    private String provincia;
    private String correo;
    private String username;
    private String password;
    private String rol;
    private String cargo;
    private String estado;
    private Date fecha_nac;
    private String comentario;

}
