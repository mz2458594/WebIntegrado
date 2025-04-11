package com.example.domain.ecommerce.dto;

import java.util.List;

import com.example.domain.ecommerce.models.entities.Producto;

public class RequestDTO {
    private int id_usuario;

    private List<ItemsVentaDTO> item;


    public int getId_usuario() {
        return id_usuario;
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
        
        




    }

}