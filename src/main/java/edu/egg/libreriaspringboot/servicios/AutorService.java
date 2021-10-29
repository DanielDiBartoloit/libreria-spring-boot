package edu.egg.libreriaspringboot.servicios;

import edu.egg.libreriaspringboot.entidades.Autor;
import edu.egg.libreriaspringboot.repositorios.AutorRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Service
public class AutorService {

    @Autowired
    private AutorRepositorio repositorio;

    @Transactional(readOnly = true)
    public List<Autor> obtenerAutores(){
        return repositorio.findAll();
    }

    @Transactional
    public void crear(String nombre){
        Autor autor = new Autor();
        autor.setNombre(nombre);
        autor.setAlta(true);
        repositorio.save(autor);
    }

    @Transactional
    public void eliminarAutor(Integer id){
        repositorio.deleteById(id);

    }
    @Transactional(readOnly = true)
    public List<Autor> buscarPorNombre(String keyword) {
        return repositorio.buscarPornombreEnBD(keyword);
    }

    @Transactional(readOnly = true)
    public Autor buscarPorId(Integer id) {
        Optional<Autor> autorOptional = repositorio.findById(id);
        return autorOptional.orElse(null);

    }

    @Transactional
    public void modificarAutor(Integer id, String nombre) {
        repositorio.modificarNombreAutor(id, nombre);
    }

    @Transactional
    public void habilitarAutor(Integer id) {
        repositorio.habilitarAutor(id);
    }
}
