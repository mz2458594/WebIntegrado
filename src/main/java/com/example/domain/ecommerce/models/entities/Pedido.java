package com.example.domain.ecommerce.models.entities;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.example.domain.ecommerce.models.enums.EstadoPedido;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "pedidos")
public class Pedido {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPedido;


    private Timestamp fechaPedido;

    private EstadoPedido estado;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Usuario user;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<Detalle_pedido> detallePedidos = new ArrayList<>();

    private double total;

    public Pedido() {
    }

    

    public Pedido(int idPedido, Timestamp fechaPedido, EstadoPedido estado, Usuario user,
            List<Detalle_pedido> detallePedidos, double total) {
        this.idPedido = idPedido;
        this.fechaPedido = fechaPedido;
        this.estado = estado;
        this.user = user;
        this.detallePedidos = detallePedidos;
        this.total = total;
    }



    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public Timestamp getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(Timestamp fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public List<Detalle_pedido> getDetallePedidos() {
        return detallePedidos;
    }

    public void setDetallePedidos(List<Detalle_pedido> detallePedidos) {
        this.detallePedidos = detallePedidos;
    }



    public double getTotal() {
        return total;
    }



    public void setTotal(double total) {
        this.total = total;
    }


    
}
