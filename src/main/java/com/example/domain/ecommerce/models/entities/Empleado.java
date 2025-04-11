package com.example.domain.ecommerce.models.entities;

import java.io.Serializable;
import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "empleados")
public class Empleado extends Persona implements Serializable{

    String cargo;

    public Empleado() {

    }


    public Empleado(int id, String dni, String nombre, String apellido, String telefono, Date fecha_nac,
                    Direccion direccion, String cargo) {
        super(id, dni, nombre, apellido, telefono, fecha_nac, direccion);
        this.cargo = cargo;
    }


    public String getCargo() {
        return cargo;
    }


    public void setCargo(String cargo) {
        this.cargo = cargo;
    }







}