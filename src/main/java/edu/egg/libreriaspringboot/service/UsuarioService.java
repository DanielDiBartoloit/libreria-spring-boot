package edu.egg.libreriaspringboot.service;

import edu.egg.libreriaspringboot.entity.Rol;
import edu.egg.libreriaspringboot.entity.Usuario;
import edu.egg.libreriaspringboot.exception.ExcepcionService;
import edu.egg.libreriaspringboot.repository.UsuarioRepositorio;

import edu.egg.libreriaspringboot.utilities.Validacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.regex.Pattern;


@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    private final String MENSAJE = "El username ingresado no existe %s";

    @Transactional
    public void crear(String nombre, String apellido, String correo, String clave, Rol rol) throws ExcepcionService {
        if (usuarioRepository.existsByCorreo(correo)) {
            throw new ExcepcionService("Ya existe un usuario con el correo ingresado");
        }

        validarUsuario(nombre, apellido, correo, clave);


        Usuario usuario = new Usuario();

        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setCorreo(correo);
        usuario.setClave(encoder.encode(clave)); // Encriptación de clave
        usuario.setRol(rol);
        usuario.setAlta(true);

        usuarioRepository.save(usuario);
    }

    public void validarUsuario(String nombre, String apellido, String correo, String clave) throws ExcepcionService {

        Validacion.validarEspacioVacioUsuario(nombre,apellido,correo,clave);

        Validacion.validarNombreSinNumeros(nombre, apellido);

        Validacion.validarCorreo(correo);

    }

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(MENSAJE, correo)));

        return new User(usuario.getCorreo(), usuario.getClave(), Collections.emptyList());
    }

}












