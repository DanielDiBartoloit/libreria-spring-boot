package edu.egg.libreriaspringboot.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/login")
public class GeneralControlador {


    @GetMapping()
    public ModelAndView mostrarLogin(){
    ModelAndView mav = new ModelAndView("login");
    return mav;
    }

}
