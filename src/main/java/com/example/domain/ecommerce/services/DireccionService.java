package com.example.domain.ecommerce.services;

import java.util.Optional;

import com.example.domain.ecommerce.dto.DireccionDTO;
import com.example.domain.ecommerce.models.entities.Cliente;
import com.example.domain.ecommerce.models.entities.Direccion;
import com.example.domain.ecommerce.models.entities.Empleado;
import com.example.domain.ecommerce.models.entities.Usuario;
import com.example.domain.ecommerce.repositories.ClienteDAO;
import com.example.domain.ecommerce.repositories.DireccionDAO;
import com.example.domain.ecommerce.repositories.EmpleadoDAO;
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

    @Autowired
    private ClienteDAO clienteDAO;

    @Autowired
    private EmpleadoDAO empleadoDAO;

    public Iterable<Direccion> obtenerCategorias() {
        return direccionDAO.findAll();
    }

    public void createDirection(DireccionDTO direccion, int id) {
        Direccion nueva_direccion = new Direccion();
        nueva_direccion.setCalle(direccion.getCalle());
        nueva_direccion.setCiudad(direccion.getCalle());
        nueva_direccion.setDistrito(direccion.getDistrito());
        nueva_direccion.setProvincia(direccion.getProvincia());

        Optional<Usuario> user = usuarioDAO.findById(Long.valueOf(id));

        if (user.isEmpty()) {
            throw new EntityNotFoundException("*Usuario no encontrado");
        }

        Optional<Cliente> cliente = clienteDAO.findByUsuario(user.get());
        Optional<Empleado> empleado = empleadoDAO.findByUsuario(user.get());

        if (cliente.isPresent()) {
            nueva_direccion.setPersona(cliente.get());
        } else if (empleado.isPresent()) {
            nueva_direccion.setPersona(empleado.get());
        }

        direccionDAO.save(nueva_direccion);

    }

    public Cliente updateDirection(DireccionDTO direccion, int id) {

        Optional<Usuario> user = usuarioDAO.findById(Long.valueOf(id));

        if (user.isEmpty()) {
            throw new EntityNotFoundException("*Usuario no encontrado");

        }

        Optional<Cliente> cliente = clienteDAO.findByUsuario(user.get());

        if (cliente.isEmpty()) {
            throw new EntityNotFoundException("*Cliente no encontrado");

        }

        Direccion direccionn = cliente.get().getDireccion();
        direccionn.setCalle(direccion.getCalle());
        direccionn.setCiudad(direccion.getCiudad());
        direccionn.setDistrito(direccion.getDistrito());
        direccionn.setProvincia(direccion.getProvincia());

        direccionDAO.save(direccionn);

        return cliente.get();
    }

}
