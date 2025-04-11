package com.example.domain.ecommerce.services;

import java.util.List;
import java.util.Optional;

import com.example.domain.ecommerce.dto.ProductDTO;
import com.example.domain.ecommerce.models.entities.Categoria;
import com.example.domain.ecommerce.models.entities.Producto;
import com.example.domain.ecommerce.models.entities.Usuario;
import com.example.domain.ecommerce.repositories.CategoriaDAO;
import com.example.domain.ecommerce.repositories.ProductoDAO;
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

    public void agregarProducto(ProductDTO productDTO) {
        Categoria categoria = categoriaDAO.findByNombre(productDTO.getNombre_categoria());

        Producto producto = new Producto();
        producto.setCategoria(categoria);
        producto.setDescripcion(productDTO.getDescripcion());
        producto.setImagen(productDTO.getImagen1());
        producto.setNombre(productDTO.getNombre());
        producto.setPrecio(productDTO.getPrecio());
        //producto.setProveedor(productDTO.get);
        producto.setStock(productDTO.getStock());

        productoDAO.save(producto);

    }

    public void actualizarProducto(ProductDTO productDTO, int id) {

        Categoria categoria = categoriaDAO.findByNombre(productDTO.getNombre_categoria());

        Producto producto = productoDAO.findById(Long.valueOf(id)).get();
        producto.setCategoria(categoria);
        producto.setDescripcion(productDTO.getDescripcion());
        producto.setNombre(productDTO.getNombre());
        producto.setPrecio(productDTO.getPrecio());
        //producto.setProveedor(null);
        producto.setStock(productDTO.getStock());

        productoDAO.save(producto);

    }

    public void eliminarProducto(int id) {
        productoDAO.deleteById(Long.valueOf(id));
    }

    public void actualizarStocks(int id_producto, int cantidad) {

        Producto producto = productoDAO.findById(Long.valueOf(id_producto)).get();
        int stock = Integer.parseInt(producto.getStock());

        producto.setStock(String.valueOf(stock - cantidad));


    }
}