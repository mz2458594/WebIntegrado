package com.example.domain.ecommerce.models.entities;

import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "empleados")
public class Empleado extends Persona implements Serializable{

    private String cargo;

    @OneToOne
    private Usuario usuario;


    public Empleado() {

    }

    public Empleado(int id, String dni, String nombre, String apellido, String telefono, Direccion direccion,
            String cargo, Usuario usuario) {
        super(id, dni, nombre, apellido, telefono, direccion);
        this.cargo = cargo;
        this.usuario = usuario;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }


}