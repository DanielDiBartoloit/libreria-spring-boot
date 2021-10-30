package edu.egg.libreriaspringboot.servicios;

import edu.egg.libreriaspringboot.entidades.Libro;
import edu.egg.libreriaspringboot.repositorios.AutorRepositorio;
import edu.egg.libreriaspringboot.repositorios.EditorialRepositorio;
import edu.egg.libreriaspringboot.repositorios.LibroRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LibroService {

    @Autowired
    private LibroRepositorio libroRepositorio;

    @Autowired
    private AutorRepositorio autorRepositorio;

    @Autowired
    private EditorialRepositorio editorialRepositorio;

    @Transactional(readOnly = true)
    public List<Libro> obtenerLibros(){
        return libroRepositorio.findAll();
    }

    @Transactional
    public void crear(Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, Integer idAutor, Integer idEditorial) {
        Libro libro = new Libro();
        libro.setAlta(true);
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setAnio(anio);
        libro.setEjemplares(ejemplares);
        libro.setEjemplaresPrestados(ejemplaresPrestados);
        libro.setEjemplaresRestantes(ejemplares - ejemplaresPrestados);
        libro.setAutor(autorRepositorio.findById(idAutor).orElse(null));
        libro.setEditorial(editorialRepositorio.findById(idEditorial).orElse(null));
        libroRepositorio.save(libro);
    }

    @Transactional
    public void eliminarLibro(Integer id){
        libroRepositorio.deleteById(id);
    }

    @Transactional
    public void habilitarLibro(Integer id) {
        libroRepositorio.habilitarLibro(id);
    }

}
