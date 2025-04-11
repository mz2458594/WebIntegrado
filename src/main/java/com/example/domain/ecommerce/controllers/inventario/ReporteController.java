package com.example.domain.ecommerce.controllers.inventario;

import com.example.domain.ecommerce.models.entities.Categoria;
import com.example.domain.ecommerce.models.entities.Proveedor;
import com.example.domain.ecommerce.models.entities.Usuario;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.Document;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.*;
import org.springframework.http.HttpHeaders;

import java.io.ByteArrayOutputStream;

@RestController
public class ReporteController extends AbstractPdfView {

    @PostMapping("/reporte-pdf")
    public void generarReportePDF(@RequestBody List<String> productos, HttpServletResponse response) {
        try {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=Reporte_Productos.pdf");

            OutputStream out = response.getOutputStream();
            Document document = new Document();
            PdfWriter.getInstance(document, out);

            document.open();
            document.add(new Paragraph("Reporte de Productos Filtrados",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18)));
            document.add(new Paragraph(" ")); // Espacio en blanco

            PdfPTable table = new PdfPTable(7); // 7 columnas
            table.setWidthPercentage(100);
            table.addCell("ID");
            table.addCell("Nombre");
            table.addCell("Categoria");
            table.addCell("Proveedor");
            table.addCell("Descripcion");
            table.addCell("Precio");
            table.addCell("Stock");

            for (String producto : productos) {
                String[] atributos = producto.split(",");

                // Asegurarse de que la cadena contiene el número esperado de atributos
                if (atributos.length == 7) {
                    table.addCell(atributos[0]); // ID Producto
                    table.addCell(atributos[1]); // Nombre
                    table.addCell(atributos[2]); // Categoria
                    table.addCell(atributos[3]); // Proveedor
                    table.addCell(atributos[4]); // Descripción
                    table.addCell(atributos[5]); // Precio
                    table.addCell(atributos[6]); // Stock
                }
            }

            document.add(table);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/stock-pdf")
    public void stockPdf(@RequestBody List<String> productos, HttpServletResponse response) {
        try {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=Reporte_stock.pdf");

            OutputStream out = response.getOutputStream();
            Document document = new Document();
            PdfWriter.getInstance(document, out);

            document.open();
            document.add(new Paragraph("Reporte de Stock",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18)));
            document.add(new Paragraph(" ")); // Espacio en blanco

            PdfPTable table = new PdfPTable(5); 
            table.setWidthPercentage(100);
            table.addCell("ID");
            table.addCell("Nombre");
            table.addCell("Categoria");
            table.addCell("Proveedor");
            table.addCell("Stock");

            for (String producto : productos) {
                String[] atributos = producto.split(",");

                // Asegurarse de que la cadena contiene el número esperado de atributos
                if (atributos.length == 5) {
                    table.addCell(atributos[0]); // ID Producto
                    table.addCell(atributos[1]); // Nombre
                    table.addCell(atributos[2]); // Categoria
                    table.addCell(atributos[3]); // Proveedor
                    table.addCell(atributos[4]); // Stock
                }
            }

            document.add(table);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'buildPdfDocument'");
    }

    @PostMapping("/reporteUsuarios-pdf")
    public void generarReportePDFUsuarios(@RequestBody List<Usuario> usuarios, HttpServletResponse response) {
        try {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=Reporte_Usuarios.pdf");

            OutputStream out = response.getOutputStream();
            Document document = new Document();
            PdfWriter.getInstance(document, out);

            document.open();
            document.add(new Paragraph("Reporte de Usuarios Filtrados",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18)));
            document.add(new Paragraph(" ")); // Espacio en blanco

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.addCell("ID");
            table.addCell("Nombre");
            table.addCell("Email");
            table.addCell("Rol");

            for (Usuario usuario : usuarios) {
                table.addCell(String.valueOf(usuario.getIdUsuario()));
                table.addCell(usuario.getPersona().getNombre());
                table.addCell(usuario.getEmail());
                table.addCell(usuario.getRole());
            }

            document.add(table);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/reporteCategorias-pdf")
    public void generarReportePDFCategorias(@RequestBody List<Categoria> categorias, HttpServletResponse response) {
        try {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=Reporte_Categorias.pdf");

            OutputStream out = response.getOutputStream();
            Document document = new Document();
            PdfWriter.getInstance(document, out);

            document.open();
            document.add(new Paragraph("Reporte de Categorias Filtrados",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18)));
            document.add(new Paragraph(" ")); // Espacio en blanco

            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100);
            table.addCell("ID");
            table.addCell("Nombre");
            table.addCell("Descripcion");

            for (Categoria categoria : categorias) {
                table.addCell(String.valueOf(categoria.getId_categoria()));
                table.addCell(categoria.getNombre());
                table.addCell(categoria.getDescripcion());
            }

            document.add(table);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/reporteProveedores-pdf")
    public void generarReportePDFProveedores(@RequestBody List<Proveedor> proveedores, HttpServletResponse response) {
        try {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=Reporte_Proveedores.pdf");

            OutputStream out = response.getOutputStream();
            Document document = new Document();
            PdfWriter.getInstance(document, out);

            document.open();
            document.add(new Paragraph("Reporte de Categorias Filtrados",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18)));
            document.add(new Paragraph(" ")); // Espacio en blanco

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.addCell("ID");
            table.addCell("Nombre");
            table.addCell("Telefono");
            table.addCell("Email");

            for (Proveedor proveedor : proveedores) {
                table.addCell(String.valueOf(proveedor.getRuc()));
                table.addCell(proveedor.getNombre());
                table.addCell(String.valueOf(proveedor.getTelefono()));
                table.addCell(proveedor.getEmail());
            }

            document.add(table);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/generarExcel")
    public ResponseEntity<byte[]> generarExcel(@RequestBody List<String> productos) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Productos");

            // Crear encabezado
            String[] encabezados = { "ID", "Nombre", "Categoría", "Proveedor", "Descripción", "Precio", "Stock" };
            Row headerRow = sheet.createRow(0);

            for (int i = 0; i < encabezados.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(encabezados[i]);
            }

            // Llenar filas con los datos de productos
            int rowNum = 1;

            for (String producto : productos) {
                String[] atributos = producto.split(",");

                // Asegurarse de que la cadena contiene el número esperado de atributos
                if (atributos.length == 7) {
                    Row row = sheet.createRow(rowNum++);

                    row.createCell(0).setCellValue(atributos[0]);
                    row.createCell(1).setCellValue(atributos[1]);
                    row.createCell(2).setCellValue(atributos[2]);
                    row.createCell(3).setCellValue(atributos[3]);
                    row.createCell(4).setCellValue(atributos[4]);
                    row.createCell(5).setCellValue(atributos[5]);
                    row.createCell(6).setCellValue(atributos[6]);
                }
            }

            // Ajustar columnas automáticamente
            for (int i = 0; i < encabezados.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Escribir el Excel en un flujo de bytes
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);

            // Preparar la respuesta
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reporte_productos.xlsx")
                    .body(out.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/stock-excel")
    public ResponseEntity<byte[]> stockPdf(@RequestBody List<String> productos) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Stock");

            // Crear encabezado
            String[] encabezados = { "ID", "Nombre", "Categoría", "Proveedor", "Stock" };
            Row headerRow = sheet.createRow(0);

            for (int i = 0; i < encabezados.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(encabezados[i]);
            }

            // Llenar filas con los datos de productos
            int rowNum = 1;

            for (String producto : productos) {
                String[] atributos = producto.split(",");

                // Asegurarse de que la cadena contiene el número esperado de atributos
                if (atributos.length == 5) {
                    Row row = sheet.createRow(rowNum++);

                    row.createCell(0).setCellValue(atributos[0]);
                    row.createCell(1).setCellValue(atributos[1]);
                    row.createCell(2).setCellValue(atributos[2]);
                    row.createCell(3).setCellValue(atributos[3]);
                    row.createCell(4).setCellValue(atributos[4]);
                }
            }

            // Ajustar columnas automáticamente
            for (int i = 0; i < encabezados.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Escribir el Excel en un flujo de bytes
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);

            // Preparar la respuesta
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reporte_stock.xlsx")
                    .body(out.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }


    @PostMapping("/excelProv")
    public ResponseEntity<byte[]> excelProv(@RequestBody List<Proveedor> proveedores) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Proveedores");

            // Crear encabezado
            String[] encabezados = { "RUC", "Nombre", "Telefono", "Email"};
            Row headerRow = sheet.createRow(0);

            for (int i = 0; i < encabezados.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(encabezados[i]);
            }

            // Llenar filas con los datos de productos
            int rowNum = 1;

            for (Proveedor proveedor : proveedores) {

                    Row row = sheet.createRow(rowNum++);

                    row.createCell(0).setCellValue(proveedor.getRuc());
                    row.createCell(1).setCellValue(proveedor.getNombre());
                    row.createCell(3).setCellValue(proveedor.getTelefono());
                    row.createCell(4).setCellValue(proveedor.getEmail());
                
            }

            // Ajustar columnas automáticamente
            for (int i = 0; i < encabezados.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Escribir el Excel en un flujo de bytes
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);

            // Preparar la respuesta
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reporte_proveedores.xlsx")
                    .body(out.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/excelCat")
    public ResponseEntity<byte[]> excelCat(@RequestBody List<Categoria> categorias) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Categorias");

            // Crear encabezado
            String[] encabezados = { "ID", "Nombre", "Descripcion"};
            Row headerRow = sheet.createRow(0);

            for (int i = 0; i < encabezados.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(encabezados[i]);
            }

            // Llenar filas con los datos de productos
            int rowNum = 1;

            for (Categoria categoria : categorias) {

                    Row row = sheet.createRow(rowNum++);

                    row.createCell(0).setCellValue(categoria.getId_categoria());
                    row.createCell(1).setCellValue(categoria.getNombre());
                    row.createCell(2).setCellValue(categoria.getDescripcion());
                
            }

            // Ajustar columnas automáticamente
            for (int i = 0; i < encabezados.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Escribir el Excel en un flujo de bytes
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);

            // Preparar la respuesta
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reporte_categorias.xlsx")
                    .body(out.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }


    @PostMapping("/excelUsuarios")
    public ResponseEntity<byte[]> excelUsuarios(@RequestBody List<Usuario> usuarios) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Usuarios");

            // Crear encabezado
            String[] encabezados = { "ID", "Nombre", "Email", "Rol"};
            Row headerRow = sheet.createRow(0);

            for (int i = 0; i < encabezados.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(encabezados[i]);
            }

            // Llenar filas con los datos de productos
            int rowNum = 1;

            for (Usuario usuario : usuarios) {

                    Row row = sheet.createRow(rowNum++);

                    row.createCell(0).setCellValue(usuario.getIdUsuario());
                    row.createCell(1).setCellValue(usuario.getPersona().getNombre());
                    row.createCell(2).setCellValue(usuario.getEmail());
                    row.createCell(3).setCellValue(usuario.getRole());
            }

            // Ajustar columnas automáticamente
            for (int i = 0; i < encabezados.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Escribir el Excel en un flujo de bytes
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);

            // Preparar la respuesta
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reporte_usuarios.xlsx")
                    .body(out.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }
}
