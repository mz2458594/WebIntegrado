package com.example.domain.ecommerce.models.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.example.domain.ecommerce.models.enums.Estado;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUsuario;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;
    

    @Column(nullable = false, unique = true)
    private String email;   

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    @JsonIgnore
    List<Venta> ventas = new ArrayList<>();


    @OneToOne
    @JoinColumn(name = "rol_id", unique = true)
    private Rol rol;

    private Estado estado;
    private String comentario;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    List<Pedido> pedidos = new ArrayList<>();


    public Usuario() {

    }


    public Usuario(int idUsuario, String username, String password, String email, List<Venta> ventas, Rol rol,
            Estado estado, String comentario, List<Pedido> pedidos) {
        this.idUsuario = idUsuario;
        this.username = username;
        this.password = password;
        this.email = email;
        this.ventas = ventas;
        this.rol = rol;
        this.estado = estado;
        this.comentario = comentario;
        this.pedidos = pedidos;
    }


    public int getIdUsuario() {
        return idUsuario;
    }


    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }


    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public List<Venta> getVentas() {
        return ventas;
    }


    public void setVentas(List<Venta> ventas) {
        this.ventas = ventas;
    }


    public Rol getRol() {
        return rol;
    }


    public void setRol(Rol rol) {
        this.rol = rol;
    }


    public Estado getEstado() {
        return estado;
    }


    public void setEstado(Estado estado) {
        this.estado = estado;
    }


    public String getComentario() {
        return comentario;
    }


    public void setComentario(String comentario) {
        this.comentario = comentario;
    }


    public List<Pedido> getPedidos() {
        return pedidos;
    }


    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    
   
}