package com.example.domain.ecommerce.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.ecommerce.dto.ProveedorDTO;
import com.example.domain.ecommerce.models.entities.Proveedor;
import com.example.domain.ecommerce.models.enums.Estado;
import com.example.domain.ecommerce.repositories.ProveedorDAO;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProveedorService {

    @Autowired
    private ProveedorDAO proveedorDAO;

    public List<Proveedor> obtenerProveedores() {
        return proveedorDAO.findAll();
    }

    public Proveedor createProv(ProveedorDTO proveedorDTO) {
        Proveedor nuevo_proveedor = new Proveedor();
        nuevo_proveedor.setRuc(proveedorDTO.getRuc());
        nuevo_proveedor.setNombre(proveedorDTO.getNombre());
        nuevo_proveedor.setEmail(proveedorDTO.getEmail());
        nuevo_proveedor.setTelefono(proveedorDTO.getTelefono());
        nuevo_proveedor.setEstado(Estado.ACTIVO);

        return proveedorDAO.save(nuevo_proveedor);

    }

    public Proveedor updateProv(ProveedorDTO proveedorDTO, int id) {

        Optional<Proveedor> prov = proveedorDAO.findById(Long.valueOf(id));

        if (prov.isEmpty()) {
            throw new EntityNotFoundException("Usuario con id " + id + " no encontrado");

        }

        Proveedor proveedor = prov.get();
        proveedor.setRuc(proveedorDTO.getRuc());
        proveedor.setNombre(proveedorDTO.getNombre());
        proveedor.setEmail(proveedorDTO.getEmail());
        proveedor.setTelefono(proveedorDTO.getTelefono());
        proveedor.setComentario(proveedorDTO.getComentario());

        if (proveedor.getEstado() != null) {
            if (proveedorDTO.getEstado().equals("ACTIVO")) {
                proveedor.setEstado(Estado.ACTIVO);
            } else if (proveedorDTO.getEstado().equals("INACTIVO")) {
                proveedor.setEstado(Estado.INACTIVO);
            }
        }

        return proveedorDAO.save(proveedor);
    }

    public void eliminarProveedor(int id) {
        proveedorDAO.deleteById(Long.valueOf(id));
    }

}
