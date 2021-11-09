package edu.egg.libreriaspringboot.service;

import edu.egg.libreriaspringboot.entity.Autor;
import edu.egg.libreriaspringboot.entity.Editorial;
import edu.egg.libreriaspringboot.entity.Libro;
import edu.egg.libreriaspringboot.exception.ExcepcionService;
import edu.egg.libreriaspringboot.repository.LibroRepositorio;
import edu.egg.libreriaspringboot.utilities.Validacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class LibroService {

    @Autowired
    private LibroRepositorio libroRepositorio;

    @Autowired
    private AutorService autorServicio;

    @Autowired
    private EditorialService editorialServicio;

    @Transactional(readOnly = true)
    public List<Libro> obtenerLibros(){
        return libroRepositorio.findAll();
    }

    @Transactional
    public void crear(Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, Autor autor, Editorial editorial) throws ExcepcionService {

        validarLibro(isbn, anio, ejemplares, ejemplaresPrestados);

        if (libroRepositorio.existsByIsbn(isbn)){
        throw new ExcepcionService("El isbn ya pertenece a otro libro");
        }

        Libro libro = new Libro();
        libro.setAlta(true);
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setAnio(anio);
        libro.setEjemplares(ejemplares);
        libro.setEjemplaresPrestados(ejemplaresPrestados);
        libro.setEjemplaresRestantes(ejemplares - ejemplaresPrestados);
        libro.setAutor(autor);
        libro.setEditorial(editorial);
        libroRepositorio.save(libro);
    }




    public void validarLibro(Long isbn, Integer anio, Integer ejemplares, Integer ejemplaresPrestados) throws ExcepcionService{
        Validacion.validarTamanioIsbn(isbn);
        Validacion.validarEjemplares(ejemplares, ejemplaresPrestados);
        Validacion.validarAnio(anio);
    }

    @Transactional
    public void eliminarLibro(Integer id){
        libroRepositorio.deleteById(id);
    }

    @Transactional
    public void habilitarLibro(Integer id) {
        libroRepositorio.habilitarLibro(id);
    }

    @Transactional(readOnly = true)
    public List<Libro> buscarPorNombre(String keyword) {
        return libroRepositorio.buscarPorTituloEnBD(keyword);
    }

    @Transactional(readOnly = true)
    public Libro buscarPorId(Integer id) {
        Optional<Libro> libroOptional = libroRepositorio.findById(id);
        return libroOptional.orElse(null);
    }

    @Transactional
    public void modificarLibro(Integer id, Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, Integer autorId, Integer editorialId) throws ExcepcionService {

        validarLibro(isbn, anio, ejemplares, ejemplaresPrestados);

        Autor autor = autorServicio.buscarPorId(autorId);
        Editorial editorial = editorialServicio.buscarPorId(editorialId);
        libroRepositorio.modificarLibro(id, isbn, titulo, anio, ejemplares, ejemplaresPrestados, autor, editorial);
    }
}












