package edu.egg.libreriaspringboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PrincipalControlador {

    @GetMapping("/")
    private ModelAndView index() {
        return new ModelAndView("index");
    }
}


