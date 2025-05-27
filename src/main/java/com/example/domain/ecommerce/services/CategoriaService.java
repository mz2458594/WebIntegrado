package com.example.domain.ecommerce.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.ecommerce.dto.CategoriaDTO;
import com.example.domain.ecommerce.models.entities.Categoria;
import com.example.domain.ecommerce.repositories.CategoriaDAO;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CategoriaService {
    @Autowired
    CategoriaDAO categoriaDAO;
    
    public Iterable<Categoria> obtenerCategorias() {
        return categoriaDAO.findAll();
    }

    public void createCategory(CategoriaDTO categoriaDTO){
        Categoria nueva_categoria = new Categoria();
        nueva_categoria.setNombre(categoriaDTO.getNombre());
        nueva_categoria.setDescripcion(categoriaDTO.getDescripcion());
        nueva_categoria.setImagen(categoriaDTO.getImagen());

        categoriaDAO.save(nueva_categoria);

    }

    public void updateCategoria(CategoriaDTO cate, int id) {

        Optional<Categoria> cat = categoriaDAO.findById(Long.valueOf(id));

        if (cat.isEmpty()) {
            throw new EntityNotFoundException("Categoria con id " + id + " no encontrado");

        }

        Categoria categoria = cat.get();
        categoria.setNombre(cate.getNombre());
        categoria.setDescripcion(cate.getDescripcion());
        categoria.setImagen(cate.getImagen());

        categoriaDAO.save(categoria);
    }


    public void eliminarCategoria(int id) {
        categoriaDAO.deleteById(Long.valueOf(id));
    }



}
