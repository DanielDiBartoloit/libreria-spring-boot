package edu.egg.libreriaspringboot.controladores;


import edu.egg.libreriaspringboot.entidades.Libro;
import edu.egg.libreriaspringboot.servicios.AutorService;
import edu.egg.libreriaspringboot.servicios.EditorialService;
import edu.egg.libreriaspringboot.servicios.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;



@Controller
@RequestMapping("libros")
public class LibroControlador {

    @Autowired
    private LibroService servicioLibro;
    @Autowired
    private AutorService servicioAutor;
    @Autowired
    private EditorialService servicioEditorial;

    @GetMapping("todos")
    public ModelAndView mostrarLibros(){
        ModelAndView mav = new ModelAndView("libros");
        List<Libro> libros = servicioLibro.obtenerLibros();
        mav.addObject("libros", libros);
        return mav;
    }

    @GetMapping("/crear")
    public ModelAndView crearLibro(){
        ModelAndView mav = new ModelAndView("libro-formulario");
        mav.addObject("libro", new Libro());
        mav.addObject("autores", servicioAutor.obtenerAutores());
        mav.addObject("editoriales",servicioEditorial.obtenerEditoriales());
        mav.addObject("title", "Libro");
        mav.addObject("action", "Crear");
        mav.addObject("seleccion", "Seleccione un Autor");
        return mav;
    }

    @PostMapping("/guardar")
    public RedirectView guardarLibro(@RequestParam Long isbn, @RequestParam String titulo, @RequestParam Integer anio, @RequestParam Integer ejemplares, @RequestParam Integer ejemplaresPrestados, @RequestParam("autor") Integer idAutor, @RequestParam("editorial") Integer idEditorial){
        servicioLibro.crear(isbn, titulo, anio, ejemplares, ejemplaresPrestados, idAutor, idEditorial);
        return new RedirectView("/libros/todos");
    }

}
