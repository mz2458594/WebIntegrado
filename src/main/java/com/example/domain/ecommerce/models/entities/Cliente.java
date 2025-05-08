package com.example.domain.ecommerce.models.entities;

import java.io.Serializable;
import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "clientes")
public class Cliente extends Persona implements Serializable{

    @OneToOne
    private Usuario usuario;

    public Cliente() {

    }

    public Cliente(int id, String dni, String nombre, String apellido, String telefono, Direccion direccion, Date fecha,
            Usuario usuario) {
        super(id, dni, nombre, apellido, telefono, direccion, fecha);
        this.usuario = usuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    



}