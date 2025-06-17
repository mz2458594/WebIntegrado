package com.example.domain.ecommerce.dto;

import lombok.Data;

@Data
public class ProductDTO {

    private String nombre;
    private String descripcion;
    private String nombre_categoria;
    private String precio;
    private String stock;
    private String imagen1;
    private String proveedor;
    private String marca;
    private String precioCompra;
    private String codigoBarras;

    // ATRIBUTOS GENERALES DE CADA TIPO
    private String sistemaOperativo;
    private String tamañoPantalla;
    private String memoriaRam;
    private String tipo;
    private String conectividad;
    private String duracion;
    private String almacenamientoInterno;
    private String resolución;

    // LAPTOP
    private String procesador;
    private String tarjetaGrafica;
    private String color;
    private String almacenamiento;

    // SMARTPHONE
    private String resolucionCamara;
    private String capacidadBateria;

    // AURICULAR
    private String cancelacionRuido;
    private String conector;

    // MONITOR
    private String tipoPanel;
    private String frecuenciaActualizacion;
    private String puertosEntrada;

    // TECLADOS
    private String distribución;
    private String retroiluminación;

    // MOUSES
    private String dpi;
    private String cantidadBotones;

    // SMARTWATCH
    private String compatibilidad;
    private String monitoreoSalud;
    private String resistenciaAgua;

    // TABLET
    private String resoluciónCamara;

    // CAMARA
    private String zoomOptico;
    private String estabilizacionImagen;

    // IMPRESORA
    private String funciones;
    private String velocidadImpresión;
    private String dobleCaraAutomatica;

}
