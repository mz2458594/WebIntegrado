package com.example.domain.ecommerce.services;

import java.time.LocalDate;
import java.time.Period;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.ecommerce.dto.UserDTO;
import com.example.domain.ecommerce.models.entities.Cliente;
import com.example.domain.ecommerce.models.entities.Direccion;
import com.example.domain.ecommerce.models.entities.Empleado;
import com.example.domain.ecommerce.models.entities.Persona;
import com.example.domain.ecommerce.models.entities.Usuario;
import com.example.domain.ecommerce.repositories.ClienteDAO;
import com.example.domain.ecommerce.repositories.EmpleadoDAO;

@Service
public class PersonaService {

    @Autowired
    private EmpleadoDAO empleadoDAO;

    @Autowired
    private ClienteDAO clienteDAO;

    public void createPersona(UserDTO user, Usuario usuario) {
        Persona persona;

        LocalDate fechaNacimineto = user.getFecha_nac().toLocalDate();

        if (user.getRol().equals("Empleado") || user.getRol().equals("Administrador")) {

            if (calcularEdad(fechaNacimineto) < 18) {
                throw new RuntimeException("No se puede registrar a un empleado menor de 18 años");
            }

            Empleado empleado = new Empleado();
            empleado.setCargo(user.getCargo());
            empleado.setUsuario(usuario);
            persona = empleado;

        } else {

            if (calcularEdad(fechaNacimineto) < 13) {
                throw new RuntimeException("No se puede registrar a un cliente menor de 13 años");
            }

            Cliente cliente = new Cliente();
            cliente.setUsuario(usuario);
            persona = cliente;
        }

        persona.setDni(user.getNum_documento());
        persona.setApellido(user.getApellido());
        persona.setNombre(user.getNombre());
        persona.setTelefono(user.getCelular());
        persona.setFecha(user.getFecha_nac());

        Direccion nueva_direccion = new Direccion();
        nueva_direccion.setCalle(user.getCalle());
        nueva_direccion.setCiudad(user.getCiudad());
        nueva_direccion.setDistrito(user.getDistrito());
        nueva_direccion.setProvincia(user.getProvincia());
        nueva_direccion.setPersona(persona);

        persona.setDireccion(nueva_direccion);

        if (persona instanceof Cliente) {
            clienteDAO.save((Cliente)  persona);
        } else if (persona instanceof Empleado){
            empleadoDAO.save((Empleado) persona);
        }

    }

      private int calcularEdad(LocalDate fecha) {
        return Period.between(fecha, LocalDate.now()).getYears();
    }


}
