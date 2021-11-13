package edu.egg.libreriaspringboot.service;

import edu.egg.libreriaspringboot.entity.Rol;
import edu.egg.libreriaspringboot.exception.ExcepcionService;
import edu.egg.libreriaspringboot.repository.RolRepositorio;
import edu.egg.libreriaspringboot.utilities.Validacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RolService {

    @Autowired
    private RolRepositorio repositorioRol;

    @Transactional
    public void crearRol(String nombre) throws ExcepcionService {

        Validacion.validarEspacioVacio(nombre);

        Rol rol = new Rol();
        rol.setNombre(nombre);
        repositorioRol.save(rol);
    }

    @Transactional(readOnly = true)
    public List<Rol> buscarTodos(){
        return repositorioRol.findAll();
    }

}
