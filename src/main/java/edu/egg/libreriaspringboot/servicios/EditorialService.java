package edu.egg.libreriaspringboot.servicios;

import edu.egg.libreriaspringboot.entidades.Autor;
import edu.egg.libreriaspringboot.entidades.Editorial;
import edu.egg.libreriaspringboot.excepciones.ExcepcionService;
import edu.egg.libreriaspringboot.repositorios.EditorialRepositorio;
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
    public List<Editorial> obtenerEditoriales(){
        return repositorio.findAll();
    }

    @Transactional
    public void crear(String nombre) throws ExcepcionService {

        Optional <Editorial> nombreEditorial = repositorio.buscarEditorialPorNombre(nombre);
        if (nombreEditorial.isPresent()){
            throw new ExcepcionService("La editorial ya se encuentra registrada");
        }

        Editorial editorial = new Editorial();
        editorial.setNombre(nombre);
        editorial.setAlta(true);
        repositorio.save(editorial);
    }


    @Transactional
    public void eliminarEditorial(Integer id){
        repositorio.deleteById(id);
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
        Optional <Editorial> nombreEditorial = repositorio.buscarEditorialPorNombre(nombre);
        if (nombreEditorial.isPresent()){
            throw new ExcepcionService("La editorial ya se encuentra registrada");
        }

        repositorio.modificarNombreEditorial(id, nombre);
    }


    @Transactional
    public void habilitarEditorial(Integer id) {
        repositorio.habilitarEditorial(id);
    }
}







