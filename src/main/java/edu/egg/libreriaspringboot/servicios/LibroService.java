package edu.egg.libreriaspringboot.servicios;

import edu.egg.libreriaspringboot.entidades.Autor;
import edu.egg.libreriaspringboot.entidades.Editorial;
import edu.egg.libreriaspringboot.entidades.Libro;
import edu.egg.libreriaspringboot.excepciones.ExcepcionService;
import edu.egg.libreriaspringboot.repositorios.AutorRepositorio;
import edu.egg.libreriaspringboot.repositorios.EditorialRepositorio;
import edu.egg.libreriaspringboot.repositorios.LibroRepositorio;
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
    public void crear(Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, Integer idAutor, Integer idEditorial) throws ExcepcionService {
        Libro libro = new Libro();
        libro.setAlta(true);

        Optional<Libro> isbnLibro = libroRepositorio.buscarLibroPorIsbn(isbn);
        if (isbnLibro.isPresent()){
        throw new ExcepcionService("El isbn ya pertenece a otro libro");
        }

        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setAnio(anio);
        libro.setEjemplares(ejemplares);
        libro.setEjemplaresPrestados(ejemplaresPrestados);
        libro.setEjemplaresRestantes(ejemplares - ejemplaresPrestados);
        libro.setAutor(autorServicio.buscarPorId(idAutor));
        libro.setEditorial(editorialServicio.buscarPorId(idEditorial));
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
    public void modificarAutor(Integer id, Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, Integer autorId, Integer editorialId) {
    Autor autor = autorServicio.buscarPorId(autorId);
    Editorial editorial = editorialServicio.buscarPorId(editorialId);
    libroRepositorio.modificarLibro(id, isbn, titulo, anio, ejemplares, ejemplaresPrestados, autor, editorial);

    }

}




