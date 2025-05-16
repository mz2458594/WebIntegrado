package com.example.domain.ecommerce.services;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.domain.ecommerce.models.entities.Cliente;
import com.example.domain.ecommerce.models.entities.Comprobante;
import com.example.domain.ecommerce.models.entities.Detalle_venta;
import com.example.domain.ecommerce.models.entities.Empleado;
import com.example.domain.ecommerce.models.entities.Usuario;
import com.example.domain.ecommerce.models.entities.Venta;
import com.example.domain.ecommerce.repositories.ClienteDAO;
import com.example.domain.ecommerce.repositories.EmpleadoDAO;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.io.util.StreamUtil;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

@Service
public class PdfGeneratorService {

    @Autowired
    private ClienteDAO clienteDAO;

    @Autowired
    private EmpleadoDAO empleadoDAO;

    public ByteArrayOutputStream generarBoletaPDF(Comprobante comprobante) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PageSize pageSize = new PageSize(226, PageSize.A4.getHeight());
        PdfDocument pdfDoc = new PdfDocument(writer);
        pdfDoc.setDefaultPageSize(pageSize);
        Document document = new Document(pdfDoc); // Tamaño pequeño vertical

        document.setMargins(20, 20, 20, 20);

        // 1. Logo (si tienes uno)

        try {
            InputStream is = getClass().getResourceAsStream("/static/images/logo.png");
            if (is == null) {
                throw new FileNotFoundException("No se encontró la imagen: /images/logo.png");
            }

            ImageData imageData = ImageDataFactory.create(StreamUtil.inputStreamToArray(is));
            Image image = new Image(imageData);
            document.add(image);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al generar el PDF", e);
        }

        // 2. Datos empresa
        document.add(new Paragraph("TARGUS S.A.C").setBold().setTextAlignment(TextAlignment.CENTER).setFontSize(10));
        document.add(new Paragraph("RUC: 1234567890").setTextAlignment(TextAlignment.CENTER).setFontSize(10));
        document.add(new Paragraph("CALLE LAS LOMAS 123\nTel: 987 654 321\nCorreo: Targusgaming@tgame.com")
                .setTextAlignment(TextAlignment.CENTER));

        document.add(new Paragraph("\nBOLETA DE VENTA ELECTRONICA")
                .setBold().setTextAlignment(TextAlignment.CENTER).setFontSize(10));
        document.add(new Paragraph(comprobante.getNumero()).setTextAlignment(TextAlignment.CENTER).setFontSize(10));

        // 3. Cliente
        Venta venta = comprobante.getVenta();
        Usuario usuario = venta.getUsuario();

        document.add(new Paragraph("\nCliente: " + usuario.getUsername()).setBold());

        Optional<Cliente> cliente = clienteDAO.findByUsuario(usuario);
        Optional<Empleado> empleado = empleadoDAO.findByUsuario(usuario);

        if (cliente.isPresent()) {
            Cliente cliente2 = cliente.get();
            document.add(new Paragraph("DNI: " + cliente2.getDni()));
        } else if (empleado.isPresent()) {
            Empleado empleado2 = empleado.get();
            document.add(new Paragraph("DNI: " + empleado2.getDni()));
        } else {
            document.add(new Paragraph("DNI: No disponible"));
        }

        // 4. Fecha y Hora
        LocalDateTime fecha = comprobante.getFechaEmision();
        document.add(
                new Paragraph("\nFECHA: " + fecha.toLocalDate() + "    \nHORA: " + fecha.toLocalTime().withSecond(0)));

        // 5. Tabla de productos
        float[] columnWidths = { 50F, 100F, 50F, 60F };
        Table table = new Table(UnitValue.createPercentArray(columnWidths)).useAllAvailableWidth();
        table.addHeaderCell("Cant");
        table.addHeaderCell("Descripción");
        table.addHeaderCell("P.U.");
        table.addHeaderCell("Total");

        for (Detalle_venta item : venta.getVentaProductos()) {
            table.addCell(item.getCantidad() + " UND");
            table.addCell(item.getProducto().getNombre());
            table.addCell(item.getProducto().getPrecioVenta());
            table.addCell(String.format("%.2f", item.getSubtotal()));
        }
        document.add(table);

        // 6. Totales
        double subtotal = venta.getTotal();
        double igv = subtotal * 0.18;
        double total = subtotal;

        document.add(new Paragraph("\nSUBTOTAL: S/ " + String.format("%.2f", subtotal)));
        document.add(new Paragraph("IGV (18%): S/ " + String.format("%.2f", igv)));
        document.add(new Paragraph("TOTAL: S/ " + String.format("%.2f", total)).setBold());

        // 7. Método de pago y otros
        document.add(new Paragraph("\nMETODO DE PAGO: EFECTIVO"));
        document.add(new Paragraph("VUELTO: 0.00"));
        document.add(new Paragraph("Observaciones:"));

        // 8. Pie de página
        document.add(new Paragraph("\nRepresentación impresa de boleta electrónica.")
                .setFontSize(8).setTextAlignment(TextAlignment.CENTER).setFontSize(10));
        document.add(new Paragraph("www.targus.com.pe").setFontSize(8).setTextAlignment(TextAlignment.CENTER)
                .setFontSize(10));

        document.close();
        return baos;
    }
}
