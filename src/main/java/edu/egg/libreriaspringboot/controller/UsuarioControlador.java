package edu.egg.libreriaspringboot.controller;

import edu.egg.libreriaspringboot.entity.Rol;
import edu.egg.libreriaspringboot.entity.Usuario;
import edu.egg.libreriaspringboot.service.RolService;
import edu.egg.libreriaspringboot.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Map;

@Controller
public class UsuarioControlador {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RolService rolService;


    @GetMapping("/signup")
    public ModelAndView signup(HttpServletRequest request, Principal principal) {
        ModelAndView mav = new ModelAndView("signup");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        if (flashMap != null) {
            mav.addObject("exito", flashMap.get("exito"));
            mav.addObject("error", flashMap.get("error"));
            mav.addObject("nombre", flashMap.get("nombre"));
            mav.addObject("apellido", flashMap.get("apellido"));
            mav.addObject("correo", flashMap.get("correo"));
            mav.addObject("clave", flashMap.get("clave"));
        }

        if (principal != null) {
            mav.setViewName("redirect:/");
        }

        mav.addObject("usuario", new Usuario());
        mav.addObject("roles", rolService.buscarTodos());

        return mav;
    }

    @PostMapping("/registro")
    // @PreAuthorize("hasRole('ADMIN')") // modificar porque sino no se puede registrar
    public RedirectView crear(@RequestParam String nombre, @RequestParam String apellido, @RequestParam String correo, @RequestParam String clave, @RequestParam Rol rol, RedirectAttributes attributes) {
        RedirectView rv = new RedirectView("/login");

        try {
            //Rol rol = rolService.buscarRolId(rolId);
            usuarioService.crear(nombre, apellido, correo, clave, rol);
            attributes.addFlashAttribute("exito", "El registro ha sido realizado satisfactoriamente");

        } catch (Exception e) {
            attributes.addFlashAttribute("error", e.getMessage());
            attributes.addFlashAttribute("nombre", nombre);
            attributes.addFlashAttribute("apellido", apellido);
            attributes.addFlashAttribute("correo", correo);
            attributes.addFlashAttribute("clave", clave);

            rv.setUrl("/signup");
        }

        return rv;
    }

}






