package com.example.domain.ecommerce.services;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.example.domain.ecommerce.dto.UserDTO;
import com.example.domain.ecommerce.dto.UsuarioPersonaDTO;
import com.example.domain.ecommerce.models.entities.*;
import com.example.domain.ecommerce.models.enums.Estado;
import com.example.domain.ecommerce.repositories.ClienteDAO;
import com.example.domain.ecommerce.repositories.EmpleadoDAO;
import com.example.domain.ecommerce.repositories.RolDAO;
import com.example.domain.ecommerce.repositories.UsuarioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioDAO usuarioDAO;

    @Autowired
    EmailService emailService;

    @Autowired
    private ClienteDAO clienteDAO;

    @Autowired
    private EmpleadoDAO empleadoDAO;

    @Autowired
    private RolDAO rolDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Usuario> listarUsuario() {
        return (List<Usuario>) usuarioDAO.findAll();
    }

    public List<Cliente> listarClientes() {
        return (List<Cliente>) clienteDAO.findAll();
    }

    public List<UsuarioPersonaDTO> listarClientesYEmpleados() {
        List<UsuarioPersonaDTO> resultado = new ArrayList<>();

        List<Cliente> clientes = clienteDAO.findAll();
        for (Cliente c : clientes) {
            Usuario u = c.getUsuario();
            UsuarioPersonaDTO dto = new UsuarioPersonaDTO();

            dto.setIdUsuario(u.getIdUsuario());
            dto.setNombre(c.getNombre());
            dto.setApellido(c.getApellido());
            dto.setNum_documento(c.getDni());
            dto.setFecha(c.getFecha());
            dto.setCelular(c.getTelefono());
            dto.setCalle(c.getDireccion().getCalle());
            dto.setCiudad(c.getDireccion().getCiudad());
            dto.setDistrito(c.getDireccion().getDistrito());
            dto.setProvincia(c.getDireccion().getProvincia());
            dto.setCorreo(u.getEmail());
            dto.setUsername(u.getUsername());
            dto.setRol(u.getRol().getNombre());
            dto.setEstado(u.getEstado().name());
            dto.setComentario(u.getComentario());

            resultado.add(dto);
        }

        List<Empleado> empleados = empleadoDAO.findAll();
        for (Empleado e : empleados) {
            Usuario u = e.getUsuario();
            UsuarioPersonaDTO dto = new UsuarioPersonaDTO();

            dto.setIdUsuario(u.getIdUsuario());
            dto.setNombre(e.getNombre());
            dto.setApellido(e.getApellido());
            dto.setNum_documento(e.getDni());
            dto.setFecha(e.getFecha());
            dto.setCelular(e.getTelefono());
            dto.setCalle(e.getDireccion().getCalle());
            dto.setCiudad(e.getDireccion().getCiudad());
            dto.setDistrito(e.getDireccion().getDistrito());
            dto.setProvincia(e.getDireccion().getProvincia());
            dto.setCorreo(u.getEmail());
            dto.setUsername(u.getUsername());
            dto.setRol(u.getRol().getNombre());
            dto.setEstado(u.getEstado().name());
            dto.setComentario(u.getComentario());

            resultado.add(dto);

        }

        return resultado;
    }

    public Usuario obtenerUsuarioPorId(int id) {

        Optional<Usuario> usuario = usuarioDAO.findById(Long.valueOf(id));

        if (usuario.isEmpty()) {
            throw new EntityNotFoundException("Usuario con id " + id + " no encontrado");

        }

        return usuario.get();
    }

    public Usuario createUser(UserDTO user) {

        Usuario usuario = new Usuario();
        usuario.setUsername(user.getUsername());
        usuario.setEmail(user.getCorreo());
        usuario.setPassword(passwordEncoder.encode(user.getContraseña()));

        Optional<Rol> rol = rolDAO.findByNombre(user.getRol());
        if (rol.isPresent()) {
            usuario.setRol(rol.get());
        }

        if (user.getEstado() == null) {

            usuario.setEstado(Estado.INACTIVO);

        } else {
            switch (user.getEstado()) {
                case "ACTIVO":
                    usuario.setEstado(Estado.ACTIVO);
                    break;
                case "INACTIVO":
                    usuario.setEstado(Estado.INACTIVO);
                    break;
                default:
                    usuario.setEstado(Estado.INACTIVO);
                    break;
            }
        }

        LocalDate fechaNacimineto = user.getFecha_nac().toLocalDate();

        if (user.getRol().equals("Empleado") || user.getRol().equals("Administrador")) {

            if (calcularEdad(fechaNacimineto) < 18) {
                throw new RuntimeException("No se puede registrar a un empleado menor de 18 años");
            }

            usuarioDAO.save(usuario);

            Empleado emp = new Empleado();

            emp.setCargo(user.getCargo());
            emp.setDni(user.getNum_documento());
            emp.setApellido(user.getApellido());
            emp.setNombre(user.getNombre());
            emp.setTelefono(user.getCelular());
            emp.setFecha(user.getFecha_nac());

            Direccion nueva_direccion = new Direccion();
            nueva_direccion.setCalle(user.getCalle());
            nueva_direccion.setCiudad(user.getCiudad());
            nueva_direccion.setDistrito(user.getDistrito());
            nueva_direccion.setProvincia(user.getProvincia());
            nueva_direccion.setPersona(emp);

            emp.setDireccion(nueva_direccion);

            emp.setUsuario(usuario);

            empleadoDAO.save(emp);

            return emp.getUsuario();

        } else {

            if (calcularEdad(fechaNacimineto) < 13) {
                throw new RuntimeException("No se puede registrar a un cliente menor de 13 años");
            }

            usuarioDAO.save(usuario);

            Cliente cli = new Cliente();
            cli.setDni(user.getNum_documento());
            cli.setApellido(user.getApellido());
            cli.setNombre(user.getNombre());
            cli.setTelefono(user.getCelular());
            cli.setFecha(user.getFecha_nac());

            Direccion nueva_direccion = new Direccion();
            nueva_direccion.setCalle(user.getCalle());
            nueva_direccion.setCiudad(user.getCiudad());
            nueva_direccion.setDistrito(user.getDistrito());
            nueva_direccion.setProvincia(user.getProvincia());
            nueva_direccion.setPersona(cli);

            cli.setDireccion(nueva_direccion);
            cli.setUsuario(usuario);

            clienteDAO.save(cli);

            return cli.getUsuario();
        }

    }

    public Persona actualizarUsuarios(UserDTO userDTO, int id) {

        Usuario usuario = usuarioDAO.findById(Long.valueOf(id)).get();

        LocalDate fechaNacimineto = userDTO.getFecha_nac().toLocalDate();

        usuario.setEmail(userDTO.getCorreo());
        usuario.setUsername(userDTO.getUsername());
        usuario.setComentario(userDTO.getComentario());

        if (usuario.getRol().getNombre().equals("Empleado")) {
            if (calcularEdad(fechaNacimineto) < 18) {
                throw new RuntimeException("No se puede registrar a un empleado menor de 18 años");
            }
        } else if (usuario.getRol().getNombre().equals("Cliente")) {
            if (calcularEdad(fechaNacimineto) < 13) {
                throw new RuntimeException("No se puede registrar a un cliente menor de 13 años");
            }
        }

        if (userDTO.getEstado() != null) {

            if (userDTO.getEstado().equals("ACTIVO")) {
                usuario.setEstado(Estado.ACTIVO);
            } else if (userDTO.getEstado().equals("INACTIVO")) {
                usuario.setEstado(Estado.INACTIVO);
            }
        }

        Optional<Cliente> cliente = clienteDAO.findByUsuario(usuario);
        Optional<Empleado> empleado = empleadoDAO.findByUsuario(usuario);

        if (cliente.isPresent()) {

            Cliente cliente2 = cliente.get();

            actualizarPersona(cliente2, userDTO);

            usuarioDAO.save(usuario);

            clienteDAO.save(cliente2);

            return cliente2;

        } else if (empleado.isPresent()) {

            Empleado empleado2 = empleado.get();
            actualizarPersona(empleado2, userDTO);

            usuarioDAO.save(usuario);

            empleadoDAO.save(empleado2);

            return empleado2;

        } else {
            throw new RuntimeException("El usuario no esta asignado ni como cliente o empleado");
        }

    }

    public void actualizarPersona(Persona persona, UserDTO userDTO) {
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
    }

    public void eliminarUsuario(int id) {
        Usuario usuario = usuarioDAO.findById(Long.valueOf(id)).get();
        Optional<Empleado> empleado = empleadoDAO.findByUsuario(usuario);
        Optional<Cliente> cliente = clienteDAO.findByUsuario(usuario);

        if (cliente.isPresent()) {
            Cliente cliente2 = cliente.get();
            clienteDAO.deleteById(Long.valueOf(cliente2.getId()));
        } else if (empleado.isPresent()) {
            Empleado empleado2 = empleado.get();
            empleadoDAO.deleteById(Long.valueOf(empleado2.getId()));
        }

        usuarioDAO.deleteById(Long.valueOf(id));
    }

    public void activar(int id) {
        Optional<Usuario> usuario = usuarioDAO.findById(Long.valueOf(id));

        if (usuario.isEmpty()) {
            throw new RuntimeException("El usuario no esta asignado ni como cliente o empleado");
        }

        Usuario user = usuario.get();
        user.setEstado(Estado.ACTIVO);
        usuarioDAO.save(user);
    }

    public void enviarEmailRegistrar(Usuario usuario) {
        Email correo = new Email("mz2458594@gmail.com", usuario.getEmail(),
                "Registrar cuenta",
                usuario.getUsername(),
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c");

        emailService.sendEmailRegistrar(correo, usuario.getIdUsuario());
    }

    public void emailContraseña(String email) {
        Optional<Usuario> usuario = usuarioDAO.findByEmail(email);

        if (usuario.isEmpty()) {
            throw new RuntimeException("El usuario no esta asignado ni como cliente o empleado");
        }

        Usuario user = usuario.get();

        Email correo = new Email("mz2458594@gmail.com", email, "Recuperar Contraseña", user.getUsername(),
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c");
        emailService.sendEmailTemplate(correo, user.getIdUsuario());
    }

    public Usuario actualizarContraseña(String contraseña, int id_usuario) {

        Usuario usuario = usuarioDAO.findById(Long.valueOf(id_usuario))
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id_usuario));

        usuario.setPassword(new BCryptPasswordEncoder().encode(contraseña));

        return usuarioDAO.save(usuario);
    }

    private int calcularEdad(LocalDate fecha) {
        return Period.between(fecha, LocalDate.now()).getYears();
    }

    public boolean verificar(String correo, String num_documento, String celular) {

        Optional<Usuario> usuario = usuarioDAO.findByEmail(correo);

        if (usuario.isPresent()) {
            return true;
        }

        Optional<Cliente> cliente = clienteDAO.findByDni(num_documento);
        if (cliente.isPresent()) {
            return true;
        }

        Optional<Cliente> cliente2 = clienteDAO.findByTelefono(celular);
        if (cliente2.isPresent()) {
            return true;
        }

        return false;
    }

}
