package edu.egg.libreriaspringboot.controladores;


import edu.egg.libreriaspringboot.entidades.Libro;
import edu.egg.libreriaspringboot.servicios.AutorService;
import edu.egg.libreriaspringboot.servicios.EditorialService;
import edu.egg.libreriaspringboot.servicios.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("libros")
public class LibroControlador {

    @Autowired
    private LibroService servicioLibro;
    @Autowired
    private AutorService servicioAutor;
    @Autowired
    private EditorialService servicioEditorial;

    @GetMapping("todos")
    public ModelAndView mostrarLibros(HttpServletRequest request){
        ModelAndView mav = new ModelAndView("libros");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        if (flashMap != null){
            mav.addObject("exitoLibroCreado", flashMap.get("exito-libro-creado"));
            mav.addObject("exitoLibroModificado", flashMap.get("exito-libro-modificado"));
            mav.addObject("errorLibroCreado", flashMap.get("error-libro-creado"));
        }

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
        mav.addObject("title", "Formulario nuevo Libro");
        mav.addObject("action", "guardar");
        mav.addObject("seleccion", "Seleccione un Autor");
        return mav;
    }

    @PostMapping("/guardar")
    public RedirectView guardarLibro(@RequestParam Long isbn, @RequestParam String titulo, @RequestParam Integer anio, @RequestParam Integer ejemplares, @RequestParam Integer ejemplaresPrestados, @RequestParam("autor") Integer idAutor, @RequestParam("editorial") Integer idEditorial, RedirectAttributes attributes){

        try{
            servicioLibro.crear(isbn, titulo, anio, ejemplares, ejemplaresPrestados, idAutor, idEditorial);
            attributes.addFlashAttribute("exito-libro-creado", "Libro creado exitosamente");

        } catch (Exception e){
            attributes.addFlashAttribute("error-libro-creado", e.getMessage());
        } //

        return new RedirectView("/libros/todos");
    }







    @PostMapping("/eliminar/{id}")
    public RedirectView eliminarLibro(@PathVariable Integer id){
        servicioLibro.eliminarLibro(id);
        return new RedirectView("/libros/todos");
    }

    @PostMapping("/habilitar/{id}")
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
    public ModelAndView editarLibro(@PathVariable Integer id){
        ModelAndView mav = new ModelAndView("libro-formulario");
        mav.addObject("libro", servicioLibro.buscarPorId(id));
        mav.addObject("autores", servicioAutor.obtenerAutores());
        mav.addObject("editoriales",servicioEditorial.obtenerEditoriales());
        mav.addObject("title", "Editar Libro");
        mav.addObject("action", "modificar");
        return mav;
    }

    @PostMapping("/modificar")
    public RedirectView modificarLibro(@RequestParam Integer id, @RequestParam Long isbn, @RequestParam String titulo, @RequestParam Integer anio, @RequestParam Integer ejemplares, @RequestParam Integer ejemplaresPrestados, @RequestParam("autor") Integer autorId, @RequestParam("editorial") Integer editorialId, RedirectAttributes attributes){
        attributes.addFlashAttribute("exito-libro-modificado", "Libro modificado exitosamente");
        servicioLibro.modificarAutor(id, isbn, titulo, anio, ejemplares, ejemplaresPrestados, autorId, editorialId);
        return new RedirectView("/libros/todos");
    }

}



