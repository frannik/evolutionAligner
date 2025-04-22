
package com.fran.ogascon.contorladores;

import com.fran.ogascon.entidades.Patologia;
import com.fran.ogascon.servicios.PatologiaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/patologias")
public class PatologiaControlador {

    @Autowired
    private PatologiaServicio patologiaServicio;

    @GetMapping("/lista")
    public String listarPatologias(Model model) {
        model.addAttribute("patologias", patologiaServicio.listarPatologias());
        return "patologiasList";
    }

    @GetMapping("/registrar")
    public String formularioPatologia(Model model) {
        model.addAttribute("patologia", new Patologia());
        return "formularioPatologia.html"; 
    }

    @PostMapping("/registro")
    public String guardarPatologia(@ModelAttribute Patologia patologia) {
        patologiaServicio.guardarPatologia(patologia);
        return "redirect:/patologias/lista";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioDeEdicion(@PathVariable String id, Model model) {
        Patologia patologia = patologiaServicio.buscarPatologiaPorId(id)
                .orElseThrow(() -> new RuntimeException("Patología no encontrada"));
        model.addAttribute("patologia", patologia);
        return "formularioPatologia.html";
    }

    @PostMapping("/modificar/{id}")
    public String modificarPatologia(@PathVariable String id, @ModelAttribute Patologia patologiaDetalles) {
        patologiaServicio.actualizarPatologia(id, patologiaDetalles);
        return "redirect:/patologias/lista";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarPatologia(@PathVariable String id) {
        patologiaServicio.eliminarPatologia(id);
        return "redirect:/patologias/lista";
    }
}
