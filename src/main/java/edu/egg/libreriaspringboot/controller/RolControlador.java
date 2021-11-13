package edu.egg.libreriaspringboot.controller;

import edu.egg.libreriaspringboot.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/roles")
public class RolControlador {

    @Autowired
    private RolService servicioRol;

    


}
