package edu.egg.libreriaspringboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class LoginControlador {

    @GetMapping("/login")
    public ModelAndView login(@RequestParam(required = false) String error, @RequestParam(required = false) String logout, Principal principal) {
        ModelAndView modelAndView = new ModelAndView("login");

        if (error != null) {
            modelAndView.addObject("error", "Correo o contraseña inválida");
        }

        if (logout != null) {
            modelAndView.addObject("logout", "Ha salido correctamente de la plataforma");
        }

        if (principal != null) {
            modelAndView.setViewName("redirect:/");
        }

        return modelAndView;
    }
}
