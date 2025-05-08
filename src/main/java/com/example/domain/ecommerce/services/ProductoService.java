package com.example.domain.ecommerce.services;

import java.util.List;
import java.util.Optional;

import com.example.domain.ecommerce.dto.ProductDTO;
import com.example.domain.ecommerce.models.entities.Categoria;
import com.example.domain.ecommerce.models.entities.Producto;
import com.example.domain.ecommerce.models.entities.Proveedor;
import com.example.domain.ecommerce.models.entities.Usuario;
import com.example.domain.ecommerce.repositories.CategoriaDAO;
import com.example.domain.ecommerce.repositories.ProductoDAO;
import com.example.domain.ecommerce.repositories.ProveedorDAO;
import com.example.domain.ecommerce.repositories.UsuarioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;


@Service
public class ProductoService {
    @Autowired
    private ProductoDAO productoDAO;

    @Autowired
    private CategoriaDAO categoriaDAO;

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Autowired
    private ProveedorDAO proveedorDAO;

    public Iterable<Categoria> obtenerCategorias() {
        return categoriaDAO.findAll();
    }

    public Iterable<Usuario> obtenerUsuarios() {
        return usuarioDAO.findAll();
    }

    public Producto obtenerProductoPorId(int id) {

        Optional<Producto> producto = productoDAO.findById(Long.valueOf(id));

        if (producto.isEmpty()) {
            throw new EntityNotFoundException("Producto con id " + id + " no encontrado");
        }

        return producto.get();
    }

    public List<Producto> listarProducto() {
        return (List<Producto>) productoDAO.findAll();
    }

    public Producto agregarProducto(ProductDTO productDTO) {
        Categoria categoria = categoriaDAO.findByNombre(productDTO.getNombre_categoria());
        Proveedor proveedor = proveedorDAO.findByNombre(productDTO.getProveedor());

        Producto producto = new Producto();
        producto.setCategoria(categoria);
        producto.setDescripcion(productDTO.getDescripcion());
        producto.setImagen(productDTO.getImagen1());
        producto.setNombre(productDTO.getNombre());
        producto.setPrecioVenta(productDTO.getPrecio());
        producto.setProveedor(proveedor);
        producto.setStock(productDTO.getStock());
        producto.setMarca(productDTO.getMarca());
        producto.setPrecioCompra(productDTO.getPrecioCompra());

        return productoDAO.save(producto);

    }

    public void actualizarProducto(ProductDTO productDTO, int id) {

        Categoria categoria = categoriaDAO.findByNombre(productDTO.getNombre_categoria());
        Proveedor proveedor = proveedorDAO.findByNombre(productDTO.getProveedor());

        Producto producto = productoDAO.findById(Long.valueOf(id)).get();
        producto.setCategoria(categoria);
        producto.setDescripcion(productDTO.getDescripcion());
        producto.setNombre(productDTO.getNombre());
        producto.setPrecioVenta(productDTO.getPrecio());
        producto.setProveedor(proveedor);
        producto.setStock(productDTO.getStock());
        producto.setMarca(productDTO.getMarca());
        producto.setPrecioCompra(productDTO.getPrecioCompra());

        productoDAO.save(producto);

    }

    public void eliminarProducto(int id) {
        productoDAO.deleteById(Long.valueOf(id));
    }


    public void actualizarStockProducto(Producto producto, int cantidad) {
        Producto product = producto;
        product.setStock(String.valueOf(Integer.valueOf(product.getStock()) - cantidad));
        productoDAO.save(product);
    }

    public void devolverStock(Producto producto, int cantidad) {
        Producto product = producto;
        product.setStock(String.valueOf(Integer.valueOf(product.getStock()) + cantidad));
        productoDAO.save(product);
    }

}