package com.example.domain.ecommerce.controllers.inventario;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.domain.ecommerce.models.entities.Comprobante;
import com.example.domain.ecommerce.models.entities.PedidoProveedor;
import com.example.domain.ecommerce.services.ComprobanteService;
import com.example.domain.ecommerce.services.PdfGeneratorService;
import com.example.domain.ecommerce.services.PedidoService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/inventario/comprobante")
@AllArgsConstructor
public class BoletaController {

    private final ComprobanteService comprobanteService;

    private final PdfGeneratorService pdfGeneratorService;

    private final PedidoService pedidoService;

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> verBoleta(@PathVariable int id) {
        Comprobante comprobante = comprobanteService.obtenerComprobantePorId(id);
        ByteArrayOutputStream pdfStream = pdfGeneratorService.generarBoletaPDF(comprobante);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.inline().filename("comprobante.pdf").build());

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfStream.toByteArray());
    }

    @GetMapping("/pedido/{id}")
    public ResponseEntity<byte[]> verFacturaOrdenCompra(@PathVariable int id) {

        PedidoProveedor pedido = pedidoService.obtenerPedidoProveedorPorId(id);

        Comprobante comprobante = pedido.getComprobante();
        ByteArrayInputStream pdfStream = pdfGeneratorService.generateFacturaPDF(comprobante);
        byte[] pdfBytes;

        try {
            pdfBytes = pdfStream.readAllBytes(); // Convertir InputStream a byte[]
        } catch (com.itextpdf.io.exceptions.IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.inline().filename("pedido.pdf").build());

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfBytes);
    }

    
}
