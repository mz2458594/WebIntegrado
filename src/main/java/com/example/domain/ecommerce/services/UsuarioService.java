package com.example.domain.ecommerce.services;

import java.util.List;
import java.util.Optional;

import com.example.domain.ecommerce.dto.EmailDTO;
import com.example.domain.ecommerce.dto.LoginDTO;
import com.example.domain.ecommerce.dto.UserDTO;
import com.example.domain.ecommerce.models.entities.*;
import com.example.domain.ecommerce.repositories.CategoriaDAO;
import com.example.domain.ecommerce.repositories.PersonaDAO;
import com.example.domain.ecommerce.repositories.ProductoDAO;
import com.example.domain.ecommerce.repositories.UsuarioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;



import jakarta.persistence.EntityNotFoundException;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioDAO usuarioDAO;

    @Autowired
    private ProductoDAO productoDAO;

    @Autowired
    private CategoriaDAO categoriaDAO;

    @Autowired
    EmailService emailService;

    @Autowired
    PersonaDAO personaDAO;

    public Usuario login(LoginDTO user) {
        Optional<Usuario> usuario = usuarioDAO.findByEmail(user.getEmail());

        System.out.println(usuario);

        if (usuario.isEmpty()) {
            throw new EntityNotFoundException("Usuario no encontrado");
        }


        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if ( encoder.matches(user.getPassword(), usuario.get().getPassword()) ) {
            return usuario.get();

        } else {
            throw new EntityNotFoundException("Contraseña incorrecta");
        }

    }

    public Usuario loginEmpleado(LoginDTO user) {
        Optional<Usuario> usuario = usuarioDAO.findByEmail(user.getEmail());


        if (usuario.isEmpty()) {
            throw new EntityNotFoundException("Usuario no encontrado");
        }


        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if ( encoder.matches(user.getPassword(), usuario.get().getPassword()) ) {
           Usuario us = usuario.get();

           if (!us.getRole().equals("Empleado")) {
                throw new SecurityException("El usuario no es un empleado");
           }

           return us;

        } else {
            throw new EntityNotFoundException("Contraseña incorrecta");
        }


    }




    public List<Producto> listarProducto() {
        return (List<Producto>) productoDAO.findAll();
    }

    public List<Usuario> listarUsuario() {
        return (List<Usuario>) usuarioDAO.findAll();
    }

    public List<Categoria> listarCategoria() {
        return (List<Categoria>) categoriaDAO.findAll();
    }

    public Usuario actualizarUsuarios(UserDTO userDTO, int id) {

        Usuario usuario = usuarioDAO.findById(Long.valueOf(id)).get();

        usuario.getPersona().setNombre(userDTO.getNombre());
        usuario.getPersona().setApellido(userDTO.getApellido());
        usuario.getPersona().setDni(userDTO.getNum_documento());
        usuario.getPersona().setTelefono(userDTO.getCelular());
        usuario.getPersona().getDireccion().setCalle(userDTO.getCalle());
        usuario.getPersona().getDireccion().setCiudad(userDTO.getCiudad());
        usuario.getPersona().getDireccion().setDistrito(userDTO.getDistrito());
        usuario.getPersona().getDireccion().setProvincia(userDTO.getProvincia());
        usuario.setEmail(userDTO.getCorreo());
        usuario.setUsername(userDTO.getUsername());
        usuario.setRole(userDTO.getRol());

        usuarioDAO.save(usuario);

       return  usuario;

    }

    public void eliminarUsuario(int id) {
        usuarioDAO.deleteById(Long.valueOf(id));
    }

    public Usuario obtenerUsuarioPorId(int id) {

        Optional<Usuario> usuario = usuarioDAO.findById(Long.valueOf(id));

        if (usuario.isEmpty()) {
            throw new EntityNotFoundException("Usuario con id " + id + " no encontrado");

        }

        return usuario.get();
    }

    public void actualizarContraseña(String contraseña, int id_usuario) {

        usuarioDAO.findById(Long.valueOf(id_usuario)).get().setPassword(new BCryptPasswordEncoder().encode(contraseña));

    }

//    public void enviarEmail(Usuario usuario) {
//        Email correo = new Email("mz2458594@gmail.com", usuario.getEmail(), "Registrar cuenta",
//                usuario.getUsername(),
//                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c");
//
//        emailService.sendEmailRegistrar(correo, usuario.getIdUsuario());
//    }

    public boolean verificar(String correo, String num_documento, String celular) {

        List<Usuario> usuarios = (List<Usuario>) usuarioDAO.findAll();

        for (Usuario usuario : usuarios) {
            if (usuario.getEmail().equals(correo) || usuario.getPersona().getDni().equals(num_documento)
                    || usuario.getPersona().getTelefono().equals(celular)) {
                return true;
            }
        }

        return false;
    }

    // nuevos metodos
    public Usuario createUser(UserDTO user) {
        Persona nueva;

        if (user.getRol().equals("Empleado")){
            Empleado emp = new Empleado();
            emp.setCargo(user.getCargo());
            nueva = emp;
        } else {
            Cliente cli = new Cliente();
            nueva = cli;
        }

        nueva.setDni(user.getNum_documento());
        nueva.setApellido(user.getApellido());
        nueva.setNombre(user.getNombre());
        nueva.setTelefono(user.getCelular());


        Direccion nueva_direccion = new Direccion();
        nueva_direccion.setCalle(user.getCalle());
        nueva_direccion.setCiudad(user.getCiudad());
        nueva_direccion.setDistrito(user.getDistrito());
        nueva_direccion.setProvincia(user.getProvincia());
        nueva_direccion.setPersona(nueva);

        nueva.setDireccion(nueva_direccion);

        personaDAO.save(nueva);

        Usuario usuario = new Usuario();
        usuario.setUsername(user.getUsername());
        usuario.setEmail(user.getCorreo());
        usuario.setPassword(new BCryptPasswordEncoder().encode(user.getContraseña()));
        usuario.setPersona(nueva);
        usuario.setRole(user.getRol());

        return usuarioDAO.save(usuario);
    }


    public Usuario obtenerUsuarioPorCorreo (EmailDTO email) {
        Optional<Usuario> usuario = usuarioDAO.findByEmail(email.getEmail());

        if (usuario.isEmpty()) {
            throw new EntityNotFoundException("Usuario no encontrado");
        }

        return usuario.get();

    }
}
