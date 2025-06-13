package com.example.domain.ecommerce.services;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.example.domain.ecommerce.dto.ProductDTO;
import com.example.domain.ecommerce.factories.ProductoFactory;
import com.example.domain.ecommerce.models.entities.Categoria;
import com.example.domain.ecommerce.models.entities.Laptop;
import com.example.domain.ecommerce.models.entities.Producto;
import com.example.domain.ecommerce.models.entities.Proveedor;
import com.example.domain.ecommerce.repositories.CategoriaDAO;
import com.example.domain.ecommerce.repositories.ProductoDAO;
import com.example.domain.ecommerce.repositories.ProveedorDAO;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductoService {

    private final ProductoDAO productoDAO;

    private final CategoriaDAO categoriaDAO;

    private final ProveedorDAO proveedorDAO;

    private final List<ProductoFactory> factories;

    public List<Categoria> obtenerCategorias() {
        return categoriaDAO.findAll();
    }

    public List<Producto> listarProducto() {
        return (List<Producto>) productoDAO.findAll();
    }

    public Producto obtenerProductoPorId(int id) {

        Optional<Producto> producto = productoDAO.findById(Long.valueOf(id));

        if (producto.isEmpty()) {
            throw new EntityNotFoundException("Producto con id " + id + " no encontrado");
        }

        return producto.get();
    }

    public Map<String, String> obtenerDetalleProducto(Producto producto) {
        Map<String, String> detalles = new LinkedHashMap<>();
        if (producto instanceof Laptop laptop) {
            detalles.put("Memoria RAM", laptop.getMemoriaRam());
            detalles.put("Procesador", laptop.getProcesador());
            detalles.put("Tarjeta Grafica", laptop.getTarjetaGrafica());
            detalles.put("Sistema Operativo", laptop.getSistemaOperativo());
            detalles.put("Tamaño de Pantalla", laptop.getTamañoPantalla());
            detalles.put("Color", laptop.getColor());
        }

        return detalles;
    }

    public Producto agregarProducto(ProductDTO productDTO) {

        Producto producto = factories.stream()
                .filter(f -> f.supports(productDTO.getNombre_categoria()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Tipo de producto no soportado"))
                .crearProducto(productDTO);

        Categoria categoria = categoriaDAO.findByNombre(productDTO.getNombre_categoria());
        Proveedor proveedor = proveedorDAO.findByNombre(productDTO.getProveedor());

        producto.setCategoria(categoria);
        producto.setDescripcion(productDTO.getDescripcion());
        producto.setImagen(productDTO.getImagen1());
        producto.setNombre(productDTO.getNombre());
        producto.setPrecioVenta(productDTO.getPrecio());
        producto.setProveedor(proveedor);
        producto.setStock(productDTO.getStock());
        producto.setMarca(productDTO.getMarca());
        producto.setPrecioCompra(productDTO.getPrecioCompra());

        if (producto.validarCodigo(productDTO.getCodigoBarras())) {
            producto.setCodigoBarras(productDTO.getCodigoBarras());
        } else {
            throw new IllegalArgumentException("El codigo de barras ingresado no es válido.");
        }

        return productoDAO.save(producto);

    }

    public Producto actualizarProducto(ProductDTO productDTO, int id) {

        Producto producto = factories.stream()
                .filter(f -> f.supports(productDTO.getNombre_categoria()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Tipo de producto no soportado"))
                .actualizar(productDTO, id);

        Categoria categoria = categoriaDAO.findByNombre(productDTO.getNombre_categoria());
        Proveedor proveedor = proveedorDAO.findByNombre(productDTO.getProveedor());

        producto.setCategoria(categoria);
        producto.setDescripcion(productDTO.getDescripcion());
        producto.setNombre(productDTO.getNombre());
        producto.setPrecioVenta(productDTO.getPrecio());
        producto.setProveedor(proveedor);
        producto.setStock(productDTO.getStock());
        producto.setMarca(productDTO.getMarca());
        producto.setPrecioCompra(productDTO.getPrecioCompra());

        if (producto.validarCodigo(productDTO.getCodigoBarras())) {
            producto.setCodigoBarras(productDTO.getCodigoBarras());
        } else {
            throw new IllegalArgumentException("El codigo de barras ingresado no es válido.");
        }

        return productoDAO.save(producto);

    }

    public void eliminarProducto(int id) {
        productoDAO.deleteById(Long.valueOf(id));
    }

    public void actualizarStockProducto(Producto producto, int cantidad) {
        Producto product = producto;
        product.setStock(String.valueOf(Integer.valueOf(product.getStock()) - cantidad));
        productoDAO.save(product);
    }

    public void aumentarStock(Producto producto, int cantidad) {
        Producto product = producto;
        product.setStock(String.valueOf(Integer.valueOf(product.getStock()) + cantidad));
        productoDAO.save(product);
    }

}