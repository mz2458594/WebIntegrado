package com.example.domain.ecommerce.services;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.example.domain.ecommerce.dto.UserDTO;
import com.example.domain.ecommerce.models.entities.Cliente;
import com.example.domain.ecommerce.models.entities.Direccion;
import com.example.domain.ecommerce.models.entities.Empleado;
import com.example.domain.ecommerce.models.entities.Persona;
import com.example.domain.ecommerce.models.entities.Usuario;
import com.example.domain.ecommerce.repositories.ClienteDAO;
import com.example.domain.ecommerce.repositories.EmpleadoDAO;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PersonaService {

    private final EmpleadoDAO empleadoDAO;

    private final ClienteDAO clienteDAO;

    private final DireccionService direccionService;

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

        Direccion nueva_direccion = direccionService.createDirection(user, persona);

        persona.setDireccion(nueva_direccion);

        if (persona instanceof Cliente) {
            clienteDAO.save((Cliente) persona);
        } else if (persona instanceof Empleado) {
            empleadoDAO.save((Empleado) persona);
        }

    }

    public Persona actualizarPersona(UserDTO userDTO, Usuario usuario) {

        LocalDate fechaNacimineto = userDTO.getFecha_nac().toLocalDate();
        if (usuario.getRol().getNombre().equals("Empleado")) {
            if (calcularEdad(fechaNacimineto) < 18) {
                throw new RuntimeException("*No se puede registrar a un empleado menor de 18 años");
            }
        } else if (usuario.getRol().getNombre().equals("Cliente")) {
            if (calcularEdad(fechaNacimineto) < 13) {
                throw new RuntimeException("*No se puede registrar a un cliente menor de 13 años");
            }
        }

        Persona persona;

        Optional<Cliente> cliente = clienteDAO.findByUsuario(usuario);
        Optional<Empleado> empleado = empleadoDAO.findByUsuario(usuario);

        if (cliente.isPresent()) {
            Cliente cliente2 = cliente.get();
            persona = cliente2;
        } else if (empleado.isPresent()) {
            Empleado empleado2 = empleado.get();
            empleado2.setCargo(userDTO.getCargo());
            persona = empleado2;
            
        } else {
            throw new RuntimeException("*El usuario no tiene ningun rol en el sistema");
        }

        persona.setNombre(userDTO.getNombre());
        persona.setApellido(userDTO.getApellido());
        persona.setDni(userDTO.getNum_documento());
        persona.setTelefono(userDTO.getCelular());
        persona.setFecha(userDTO.getFecha_nac());

        if (userDTO.getCalle() != null && userDTO.getCiudad() != null &&
                userDTO.getDistrito() != null && userDTO.getProvincia() != null) {
            persona.getDireccion().setCalle(userDTO.getCalle());
            persona.getDireccion().setCiudad(userDTO.getCiudad());
            persona.getDireccion().setDistrito(userDTO.getDistrito());
            persona.getDireccion().setProvincia(userDTO.getProvincia());
        }

        if (persona instanceof Cliente) {
            return clienteDAO.save((Cliente) persona);
        } else if (persona instanceof Empleado) {
            return empleadoDAO.save((Empleado) persona);
        } else {
            throw new RuntimeException("Error en el sistema, comuniquese con el administrador");
        }

    }

    private int calcularEdad(LocalDate fecha) {
        return Period.between(fecha, LocalDate.now()).getYears();
    }

}
