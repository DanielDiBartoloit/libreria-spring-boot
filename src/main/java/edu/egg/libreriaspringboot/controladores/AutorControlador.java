package edu.egg.libreriaspringboot.controladores;

import edu.egg.libreriaspringboot.entidades.Autor;
import edu.egg.libreriaspringboot.servicios.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("autores")
public class AutorControlador {

    @Autowired
    private AutorService servicioAutor;

    @GetMapping("todos")
    public ModelAndView mostrarAutores(HttpServletRequest request){
        ModelAndView mav = new ModelAndView("autor");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        if (flashMap != null){
            mav.addObject("exitoAutor", flashMap.get("exito-nombre-autor"));


        }

        mav.addObject("autores", servicioAutor.obtenerAutores());
        return mav;
    }

    @GetMapping("/crear")
    public ModelAndView crearAutor(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("autor-formulario");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        Autor autor = new Autor();

        if (flashMap != null){
            autor.setNombre((String) flashMap.get("nombre-autor"));
            mav.addObject("errorAutor", flashMap.get("error-nombre-autor"));
        }

        mav.addObject("autor", autor);
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
    public RedirectView guardarAutor(@RequestParam String nombre, RedirectAttributes attributes){

        RedirectView rv = new RedirectView("/autores/todos");

        try{
            servicioAutor.crear(nombre);
            attributes.addFlashAttribute("exito-nombre-autor", "Autor creado exitosamente");
        }catch(Exception e){
            attributes.addFlashAttribute("error-nombre-autor", e.getMessage());
            attributes.addFlashAttribute("nombre-autor", nombre);
            rv.setUrl("/autores/crear");
        }
        return rv;
    }





    @PostMapping("/eliminar/{id}")
    public RedirectView eliminarAutor(@PathVariable Integer id){
        servicioAutor.eliminarAutor(id);
        return new RedirectView("/autores/todos");
    }

    @PostMapping("/habilitar/{id}")
    public RedirectView habilitarAutor(@PathVariable Integer id){
        servicioAutor.habilitarAutor(id);
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
    public RedirectView modificarAutor(@RequestParam Integer id, @RequestParam String nombre, RedirectAttributes attributes){
        servicioAutor.modificarAutor(id, nombre);
        attributes.addFlashAttribute("exito-nombre-autor", "Autor modificado exitosamente");
        return new RedirectView("/autores/todos");

    }


}











