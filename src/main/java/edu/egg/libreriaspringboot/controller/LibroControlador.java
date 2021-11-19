package edu.egg.libreriaspringboot.controller;


import edu.egg.libreriaspringboot.entity.Autor;
import edu.egg.libreriaspringboot.entity.Editorial;
import edu.egg.libreriaspringboot.entity.Libro;
import edu.egg.libreriaspringboot.service.AutorService;
import edu.egg.libreriaspringboot.service.EditorialService;
import edu.egg.libreriaspringboot.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/libros")
public class LibroControlador {

    @Autowired
    private LibroService servicioLibro;
    @Autowired
    private AutorService servicioAutor;
    @Autowired
    private EditorialService servicioEditorial;

    @GetMapping("/todos")
    public ModelAndView mostrarLibros(HttpServletRequest request){
        ModelAndView mav = new ModelAndView("libros");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        if (flashMap != null){
            mav.addObject("exitoLibroCreado", flashMap.get("exito-libro-creado"));
            mav.addObject("exitoLibroModificado", flashMap.get("exito-libro-modificado"));

        }

        List<Libro> libros = servicioLibro.obtenerLibros();
        mav.addObject("libros", libros);
        return mav;
    }


    @GetMapping("/crear")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView crearLibro(HttpServletRequest request){
        ModelAndView mav = new ModelAndView("libro-formulario");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        Libro libro = new Libro();

        if (flashMap != null){
            mav.addObject("errorLibroCreado", flashMap.get("error-libro-creado"));
            libro.setIsbn((Long) flashMap.get("isbn"));
            libro.setTitulo((String) flashMap.get("titulo"));
            libro.setAnio((Integer) flashMap.get("anio"));
            libro.setEjemplares((Integer) flashMap.get("ejemplares"));
            libro.setEjemplaresPrestados((Integer) flashMap.get("ejemplaresPrestados"));
        }

        mav.addObject("libro", libro);
        mav.addObject("autores", servicioAutor.obtenerAutores());
        mav.addObject("editoriales",servicioEditorial.obtenerEditoriales());
        mav.addObject("title", "Formulario nuevo Libro");
        mav.addObject("action", "guardar");
        mav.addObject("seleccion", "Seleccione un Autor");
        return mav;
    }

    @PostMapping("/guardar")
    @PreAuthorize("hasRole('ADMIN')")
    public RedirectView guardarLibro(@RequestParam Long isbn, @RequestParam String titulo, @RequestParam Integer anio, @RequestParam Integer ejemplares, @RequestParam Integer ejemplaresPrestados, @RequestParam Autor autor, @RequestParam("editorial") Editorial editorial, RedirectAttributes attributes){
        RedirectView rv = new RedirectView("/libros/todos");

        try{
            servicioLibro.crear(isbn, titulo, anio, ejemplares, ejemplaresPrestados, autor, editorial);
            attributes.addFlashAttribute("exito-libro-creado", "Libro creado exitosamente");

        } catch (Exception e) {
            attributes.addFlashAttribute("error-libro-creado", e.getMessage());
            attributes.addFlashAttribute("isbn", isbn);
            attributes.addFlashAttribute("titulo", titulo);
            attributes.addFlashAttribute("anio", anio);
            attributes.addFlashAttribute("ejemplares", ejemplares);
            attributes.addFlashAttribute("ejemplaresPrestados", ejemplaresPrestados);
            rv.setUrl("/libros/crear");
        }
        return rv;
    }

    @PostMapping("/eliminar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public RedirectView eliminarLibro(@PathVariable Integer id){
        servicioLibro.eliminarLibro(id);
        return new RedirectView("/libros/todos");
    }

    @PostMapping("/habilitar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public RedirectView habilitarLibro(@PathVariable Integer id){
        servicioLibro.habilitarLibro(id);
        return new RedirectView("/libros/todos");
    }

    @GetMapping("/buscar")
    public ModelAndView buscarLibrosPorNombre(@RequestParam String keyword){
        ModelAndView mav = new ModelAndView("libros");
        mav.addObject("libros", servicioLibro.buscarPorNombre(keyword));
        return mav;
    }

    @GetMapping("/editar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView editarLibro(@PathVariable Integer id, HttpServletRequest request){
        ModelAndView mav = new ModelAndView("libro-formulario");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        Libro libro = servicioLibro.buscarPorId(id);

        if (flashMap != null){
            mav.addObject("errorLibroCreado", flashMap.get("error-libro-modificado"));
            libro.setIsbn((Long) flashMap.get("isbn"));
            libro.setTitulo((String) flashMap.get("titulo"));
            libro.setAnio((Integer) flashMap.get("anio"));
            libro.setEjemplares((Integer) flashMap.get("ejemplares"));
            libro.setEjemplaresPrestados((Integer) flashMap.get("ejemplaresPrestados"));
        }

        mav.addObject("libro", libro);
        mav.addObject("autores", servicioAutor.obtenerAutores());
        mav.addObject("editoriales",servicioEditorial.obtenerEditoriales());
        mav.addObject("title", "Editar Libro");
        mav.addObject("action", "modificar");

        return mav;
    }

    @PostMapping("/modificar")
    @PreAuthorize("hasRole('ADMIN')")
    public RedirectView modificarLibro(@RequestParam Integer id, @RequestParam Long isbn, @RequestParam String titulo, @RequestParam Integer anio, @RequestParam Integer ejemplares, @RequestParam Integer ejemplaresPrestados, @RequestParam("autor") Integer autorId, @RequestParam("editorial") Integer editorialId, RedirectAttributes attributes){
        RedirectView rv = new RedirectView("/libros/todos");

        try{
            attributes.addFlashAttribute("exito-libro-modificado", "Libro modificado exitosamente");
            servicioLibro.modificarLibro(id, isbn, titulo, anio, ejemplares, ejemplaresPrestados, autorId, editorialId);
        } catch (Exception e){
            attributes.addFlashAttribute("error-libro-modificado", e.getMessage());
            attributes.addFlashAttribute("isbn", isbn);
            attributes.addFlashAttribute("titulo", titulo);
            attributes.addFlashAttribute("anio", anio);
            attributes.addFlashAttribute("ejemplares", ejemplares);
            attributes.addFlashAttribute("ejemplaresPrestados", ejemplaresPrestados);
            rv.setUrl("/libros/editar/" + id);
        }
        return rv;
    }

    @GetMapping("/ficha/{id}")

    public ModelAndView mostrarFichaLibro(@PathVariable Integer id){
        ModelAndView mav = new ModelAndView("libro-ficha");

        mav.addObject("libro", servicioLibro.buscarPorId(id));

        return mav;

    }
}


