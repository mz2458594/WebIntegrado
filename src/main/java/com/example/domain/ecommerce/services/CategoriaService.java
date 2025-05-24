package com.example.domain.ecommerce.services;

import java.util.List;
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
    
    public List<Categoria> obtenerCategorias() {
        return categoriaDAO.findAll();
    }

    public Categoria createCategory(CategoriaDTO categoriaDTO){
        Categoria nueva_categoria = new Categoria();
        nueva_categoria.setNombre(categoriaDTO.getNombre());
        nueva_categoria.setDescripcion(categoriaDTO.getDescripcion());
        nueva_categoria.setImagen(categoriaDTO.getImagen());

        return categoriaDAO.save(nueva_categoria);

    }

    public Categoria updateCategoria(CategoriaDTO cate, int id) {

        Optional<Categoria> cat = categoriaDAO.findById(Long.valueOf(id));

        if (cat.isEmpty()) {
            throw new EntityNotFoundException("Usuario con id " + id + " no encontrado");

        }

        Categoria categoria = cat.get();
        categoria.setNombre(cate.getNombre());
        categoria.setDescripcion(cate.getDescripcion());
        categoria.setImagen(cate.getImagen());

        return categoriaDAO.save(categoria);
    }


    public void eliminarCategoria(int id) {
        categoriaDAO.deleteById(Long.valueOf(id));
    }



}
