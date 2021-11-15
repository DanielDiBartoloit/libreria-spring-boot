package edu.egg.libreriaspringboot.controller;

import edu.egg.libreriaspringboot.entity.Rol;
import edu.egg.libreriaspringboot.exception.ExcepcionService;
import edu.egg.libreriaspringboot.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/roles")
public class RolControlador {

    @Autowired
    private RolService servicioRol;

    @GetMapping("/crear")
    //@PreAuthorize("hasRole('ADMIN')")
    public ModelAndView crear(){
        ModelAndView mav = new ModelAndView("rol-formulario");
        mav.addObject("titulo", "Formulario nuevo Rol");
        mav.addObject("rol", new Rol());
        mav.addObject("action", "guardar");

        return mav;
    }

    @PostMapping("/guardar")
    //@PreAuthorize("hasRole('ADMIN')")
    public RedirectView guardar(@RequestParam String nombre, RedirectAttributes attributes){
        RedirectView rv = new RedirectView("/"); //modificar

        try {
            servicioRol.crearRol(nombre);
            //attributes.addFlashAttribute("exito", "Rol cargado exitosamente");

        }catch (Exception e){
            attributes.addFlashAttribute("error", e.getMessage());
            rv.setUrl("/autores/todos"); //modificar
        }

        return  rv;
    }



}
