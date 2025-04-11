package com.example.domain.ecommerce.services;

import java.util.Optional;

import com.example.domain.ecommerce.dto.DireccionDTO;
import com.example.domain.ecommerce.models.entities.Direccion;
import com.example.domain.ecommerce.models.entities.Usuario;
import com.example.domain.ecommerce.repositories.DireccionDAO;
import com.example.domain.ecommerce.repositories.UsuarioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

@Service
public class DireccionService {
    @Autowired
    private DireccionDAO direccionDAO;

    @Autowired
    private UsuarioDAO usuarioDAO;

    public Iterable<Direccion> obtenerCategorias() {
        return direccionDAO.findAll();
    }

    public void createDirection(DireccionDTO direccion, int id){
        Direccion nueva_direccion = new Direccion();
        nueva_direccion.setCalle(direccion.getCalle());
        nueva_direccion.setCiudad(direccion.getCalle());
        nueva_direccion.setDistrito(direccion.getDistrito());
        nueva_direccion.setProvincia(direccion.getProvincia());

        Optional<Usuario> user = usuarioDAO.findById(Long.valueOf(id));

        if (user.isEmpty()) {
            throw new EntityNotFoundException("Usuario con id " + id + " no encontrado");
        }

        nueva_direccion.setPersona(user.get().getPersona());

        direccionDAO.save(nueva_direccion);

    }

    public void updateDirection(DireccionDTO direccion, int id) {

        Optional<Usuario> user = usuarioDAO.findById(Long.valueOf(id));

        if (user.isEmpty()) {
            throw new EntityNotFoundException("Usuario con id " + id + " no encontrado");

        }


        Direccion direccionn = user.get().getPersona().getDireccion();
        direccionn.setCalle(direccion.getCalle());
        direccionn.setCiudad(direccion.getCiudad());
        direccionn.setDistrito(direccion.getDistrito());
        direccionn.setProvincia(direccion.getProvincia());

        direccionDAO.save(direccionn);
    }


}
