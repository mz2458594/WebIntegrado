package com.example.domain.ecommerce.dto;

import java.util.List;

import com.example.domain.ecommerce.models.entities.Producto;

public class RequestDTO {
    private int id_usuario;

    private List<ItemsVentaDTO> item;


    public int getId_usuario() {
        return id_usuario;
    }

    private String ruc;

    private String razon;

    private String tipo;

    private float vuelto;
    private float efectivo;


    public float getVuelto() {
        return vuelto;
    }


    public void setVuelto(float vuelto) {
        this.vuelto = vuelto;
    }


    public float getEfectivo() {
        return efectivo;
    }


    public void setEfectivo(float efectivo) {
        this.efectivo = efectivo;
    }


    public String getRuc() {
        return ruc;
    }


    public void setRuc(String ruc) {
        this.ruc = ruc;
    }


    public String getRazon() {
        return razon;
    }


    public void setRazon(String razon) {
        this.razon = razon;
    }


    public String getTipo() {
        return tipo;
    }


    public void setTipo(String tipo) {
        this.tipo = tipo;
    }


    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }


    public List<ItemsVentaDTO> getItem() {
        return item;
    }


    public void setItem(List<ItemsVentaDTO> item) {
        this.item = item;
    }


    public static class ItemsVentaDTO {
        private Producto producto;
        private int cantidad;
        private float total;

        

        public Producto getProducto() {
            return producto;
        }
        public void setProducto(Producto producto) {
            this.producto = producto;
        }

        
        public int getCantidad() {
            return cantidad;
        }
        public void setCantidad(int cantidad) {
            this.cantidad = cantidad;
        }
        public float getTotal() {
            return total;
        }
        public void setTotal(float total) {
            this.total = total;
        }
        
    }

}