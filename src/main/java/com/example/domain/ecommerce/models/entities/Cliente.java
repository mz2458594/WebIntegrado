package com.example.domain.ecommerce.models.entities;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;


@Entity
@Table(name = "clientes")
public class Cliente extends Persona implements Serializable{



    public Cliente() {

    }

    public Cliente(int id, String dni, String nombre, String apellido, String telefono,
                   Direccion direccion, Usuario usuario) {
        super(id, dni, nombre, apellido, telefono, direccion);
    }



}