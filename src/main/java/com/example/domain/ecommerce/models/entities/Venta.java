package com.example.domain.ecommerce.models.entities;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ventas")
public class Venta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ventas")
    private int idVentas;

    @Column(name = "f_venta")
    private Timestamp fechaVenta;

    @Column(name = "total_venta")
    private double total;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL)
    private List<Detalle_venta> ventaProductos = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Usuario usuario;

    @OneToOne(mappedBy = "venta", cascade = CascadeType.ALL)
    private Boleta boleta;




    public Venta() {

    }

    public Venta(int idVentas, Timestamp fechaVenta, double total, List<Detalle_venta> ventaProductos,
                 Usuario usuario) {
        this.idVentas = idVentas;
        this.fechaVenta = fechaVenta;
        this.total = total;
        this.ventaProductos = ventaProductos;
        this.usuario = usuario;
    }


    public int getIdVentas() {
        return idVentas;
    }



    public void setIdVentas(int idVentas) {
        this.idVentas = idVentas;
    }



    public Timestamp getFechaVenta() {
        return fechaVenta;
    }



    public void setFechaVenta(Timestamp fechaVenta) {
        this.fechaVenta = fechaVenta;
    }



    public double getTotal() {
        return total;
    }



    public void setTotal(double total) {
        this.total = total;
    }



    public List<Detalle_venta> getVentaProductos() {
        return ventaProductos;
    }



    public void setVentaProductos(List<Detalle_venta> ventaProductos) {
        this.ventaProductos = ventaProductos;
    }



    public Usuario getUsuario() {
        return usuario;
    }



    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }



}
