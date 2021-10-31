package edu.egg.libreriaspringboot.controladores;

import edu.egg.libreriaspringboot.entidades.Editorial;
import edu.egg.libreriaspringboot.servicios.EditorialService;
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
@RequestMapping("editoriales")
public class EditorialControlador {

    @Autowired
    private EditorialService servicioEditorial;

    @GetMapping("/todos")
    private ModelAndView mostrarEditoriales(HttpServletRequest request){
        ModelAndView mav = new ModelAndView("editorial");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        if (flashMap != null){
            mav.addObject("exitoEditorialCreada", flashMap.get("exito-editorial-creada"));
            mav.addObject("exitoEditorialModificada", flashMap.get("exito-editorial-modificada"));
        }

        mav.addObject("editoriales", servicioEditorial.obtenerEditoriales());
        return mav;
    }


    @GetMapping("/crear")
    public ModelAndView crearEditorial() {
        ModelAndView mav = new ModelAndView("editorial-formulario");
        mav.addObject("editorial", new Editorial());
        mav.addObject("title", "Formulario nueva Editorial");
        mav.addObject("action", "guardar");
        return mav;
    }

    @PostMapping("/guardar")
    public RedirectView guardarEditorial(@RequestParam String nombre, RedirectAttributes attributes){
        servicioEditorial.crear(nombre);
        attributes.addFlashAttribute("exito-editorial-creada", "Editorial creada exitosamente");
        return new RedirectView("/editoriales/todos");
    }

    @PostMapping("/eliminar/{id}")
    public RedirectView eliminarEditorial(@PathVariable Integer id){
        servicioEditorial.eliminarEditorial(id);
        return new RedirectView("/editoriales/todos");
    }

    @PostMapping("/habilitar/{id}")
    public RedirectView habilitarEditorial(@PathVariable Integer id){
        servicioEditorial.habilitarEditorial(id);
        return new RedirectView("/editoriales/todos");
    }

    @GetMapping("/buscar")
    public ModelAndView buscarEditorial(@RequestParam String keyword){
        ModelAndView mav = new ModelAndView("autor");
        mav.addObject("autores", servicioEditorial.buscarPorNombre(keyword));
        return mav;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editarEditorial(@PathVariable Integer id){
        ModelAndView mav = new ModelAndView("editorial-formulario");
        mav.addObject("editorial", servicioEditorial.buscarPorId(id));
        mav.addObject("title", "Editar Editorial");
        mav.addObject("action", "modificar");
        return mav;

    }

    @PostMapping("/modificar")
    public RedirectView modificarEditorial(@RequestParam Integer id, @RequestParam String nombre, RedirectAttributes attributes){
        servicioEditorial.modificarEditorial(id, nombre);
        attributes.addFlashAttribute("exito-editorial-modificada", "Editorial modificada exitosamente");
        return new RedirectView("/editoriales/todos");

    }


}
