package edu.egg.libreriaspringboot.controladores;

import edu.egg.libreriaspringboot.entidades.Autor;
import edu.egg.libreriaspringboot.servicios.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("autores")
public class AutorControlador {

    @Autowired
    private AutorService servicioAutor;

    @GetMapping("todos")
    public ModelAndView mostrarAutores(){
        ModelAndView mav = new ModelAndView("autor");
        mav.addObject("autores", servicioAutor.obtenerAutores());
        return mav;
    }

    @GetMapping("/crear")
    public ModelAndView crearAutor() {
        ModelAndView mav = new ModelAndView("autor-formulario");
        mav.addObject("autor", new Autor());
        mav.addObject("title", "Formulario nuevo Autor");
        mav.addObject("action", "guardar");
        return mav;
    }

    @GetMapping("/buscar")
    public ModelAndView buscarAutores(@RequestParam String keyword){
        ModelAndView mav = new ModelAndView("autor");
        mav.addObject("autores", servicioAutor.buscarPorNombre(keyword));
        return mav;
    }

    @PostMapping("/guardar")
    public RedirectView guardarAutor(@RequestParam String nombre){
        servicioAutor.crear(nombre);
        return new RedirectView("/autores/todos");
    }

    @PostMapping("/eliminar/{id}")
    public RedirectView eliminarAutor(@PathVariable Integer id){
        servicioAutor.eliminarAutor(id);
        return new RedirectView("/autores/todos");
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editarAutor(@PathVariable Integer id){
        ModelAndView mav = new ModelAndView("autor-formulario");
        mav.addObject("autor", servicioAutor.buscarPorId(id));
        mav.addObject("title", "Editar Autor");
        mav.addObject("action", "modificar");
        return mav;

    }

    @PostMapping("/modificar")
    public RedirectView modificarAutor(@RequestParam Integer id, @RequestParam String nombre){
        servicioAutor.modificarAutor(id, nombre);
        return new RedirectView("/autores/todos");

    }







}






