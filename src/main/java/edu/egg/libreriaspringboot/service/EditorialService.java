package edu.egg.libreriaspringboot.service;


import edu.egg.libreriaspringboot.entity.Editorial;
import edu.egg.libreriaspringboot.exception.ExcepcionService;

import edu.egg.libreriaspringboot.repository.EditorialRepositorio;
import edu.egg.libreriaspringboot.utilities.Validacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class EditorialService {

    @Autowired
    private EditorialRepositorio repositorio;

    @Transactional(readOnly = true)
    public List<Editorial> obtenerEditoriales() {
        return repositorio.findAll();
    }

    @Transactional
    public void crear(String nombre) throws ExcepcionService {

        validarEditorial(nombre);

        Editorial editorial = new Editorial();
        editorial.setNombre(nombre);
        editorial.setAlta(true);
        repositorio.save(editorial);
    }

    @Transactional
    public void validarEditorial(String nombre) throws ExcepcionService {

        Validacion.validarEspacioVacio(nombre);

        if (repositorio.existsByNombre(nombre)) {
            throw new ExcepcionService("La editorial ya se encuentra registrada");
        }
    }

    @Transactional(readOnly = true)
    public List<Editorial> buscarPorNombre(String keyword) {
        return repositorio.buscarPornombreEnBD(keyword);
    }

    @Transactional(readOnly = true)
    public Editorial buscarPorId(Integer id) {
        Optional<Editorial> editorialOptional = repositorio.findById(id);
        return editorialOptional.orElse(null);
    }

    @Transactional
    public void modificarEditorial(Integer id, String nombre) throws ExcepcionService {

        validarEditorial(nombre);

        Editorial editorial = repositorio.findById(id).get(); //te devuelve un optional
        editorial.setNombre(nombre);
        repositorio.save(editorial);

        //repositorio.modificarNombreEditorial(id, nombre);
    }

    @Transactional
    public void eliminarEditorial(Integer id) {
        repositorio.deleteById(id);
    }

    @Transactional
    public void habilitarEditorial(Integer id) {
        repositorio.habilitarEditorial(id);
    }
}





















