package com.example.domain.ecommerce.controllers;

import java.io.ByteArrayOutputStream;

import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.domain.ecommerce.models.entities.Comprobante;
import com.example.domain.ecommerce.services.ComprobanteService;
import com.example.domain.ecommerce.services.PdfGeneratorService;

@RequestMapping("/api/comprobante")
@RestController
public class ComprobanteController {

    @Autowired
    private ComprobanteService comprobanteService;

    @Autowired
    private PdfGeneratorService pdfGeneratorService;

    @GetMapping("/boleta/{id}")
    public ResponseEntity<byte[]> generarBoleta(@PathVariable int id) {
        Comprobante comprobante = comprobanteService.obtenerComprobantePorId(id);
        ByteArrayOutputStream pdfStream = pdfGeneratorService.generarBoletaPDF(comprobante);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.inline().filename("comprobante.pdf").build());

        return ResponseEntity.ok()
                .headers(headers).body(pdfStream.toByteArray());

    }
}
