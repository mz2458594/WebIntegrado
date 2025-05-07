package com.example.domain.ecommerce.models.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.example.domain.ecommerce.models.enums.Estado;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "proveedores")
public class Proveedor implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private Long ruc;

    private String nombre;

    private int telefono;

    private String email;

    private String comentario;

    private Estado estado;


    @OneToMany (mappedBy = "proveedor", cascade = CascadeType.ALL)
    private List<Producto> productos = new ArrayList<>();


    public Proveedor() {
    }


    public Proveedor(int id, Long ruc, String nombre, int telefono, String email, String comentario, Estado estado,
            List<Producto> productos) {
        this.id = id;
        this.ruc = ruc;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.comentario = comentario;
        this.estado = estado;
        this.productos = productos;
    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public Long getRuc() {
        return ruc;
    }


    public void setRuc(Long ruc) {
        this.ruc = ruc;
    }


    public String getNombre() {
        return nombre;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public int getTelefono() {
        return telefono;
    }


    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public String getComentario() {
        return comentario;
    }


    public void setComentario(String comentario) {
        this.comentario = comentario;
    }


    public Estado getEstado() {
        return estado;
    }


    public void setEstado(Estado estado) {
        this.estado = estado;
    }


    public List<Producto> getProductos() {
        return productos;
    }


    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    




}