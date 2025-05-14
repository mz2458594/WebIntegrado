package com.example.domain.ecommerce.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.ecommerce.models.entities.Comprobante;
import com.example.domain.ecommerce.models.entities.ControlComprobante;
import com.example.domain.ecommerce.models.entities.Venta;
import com.example.domain.ecommerce.models.enums.TipoComprobante;
import com.example.domain.ecommerce.repositories.ComprobanteDAO;
import com.example.domain.ecommerce.repositories.ControlComprobanteDAO;

@Service
public class ComprobanteService {
    @Autowired
    private ComprobanteDAO comprobanteDAO;

    @Autowired
    private ControlComprobanteDAO controlComprobanteDAO;


    @Transactional
    public Comprobante generarComprobante(Venta venta, String tipo, String ruc, String razon) {

        
        TipoComprobante tip = TipoComprobante.valueOf(tipo);

        ControlComprobante control = controlComprobanteDAO.findByTipo(tip)
                .orElseThrow(() -> new RuntimeException("Control de boleta no figurado"));

        int nuevoNumero = control.getUltimoNumero() + 1;
        control.setUltimoNumero(nuevoNumero);
        control.setActualizacion(LocalDateTime.now());
        controlComprobanteDAO.save(control);

        String formato = control.getSerie() + "-" + String.format("%07d", nuevoNumero);

        Comprobante comprobante = new Comprobante();
        comprobante.setNumero(formato);
        comprobante.setFechaEmision(LocalDateTime.now());
        comprobante.setTipo(control.getTipo());
        comprobante.setVenta(venta);

        if (comprobante.getTipo() == TipoComprobante.FACTURA) {
            comprobante.setRazonSocial(razon);
            comprobante.setRucCliente(ruc);
        }

        return comprobanteDAO.save(comprobante);
    }

    public void guardarComprobante(Comprobante comprobante) {
        comprobanteDAO.save(comprobante);
    }

    public Comprobante obtenerComprobantePorId(int id){
        return comprobanteDAO.findById(Long.valueOf(id)).get();
    }

}
