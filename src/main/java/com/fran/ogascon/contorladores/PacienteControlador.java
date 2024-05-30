
package com.fran.ogascon.contorladores;

import com.fran.ogascon.entidades.Paciente;
import com.fran.ogascon.excepciones.MiException;
import com.fran.ogascon.servicios.PacienteServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/paciente")
public class PacienteControlador {
    @Autowired
    PacienteServicio pacienteServicio;
    
    @GetMapping("/registrar")
    public String registrar(){
        return "formularioPaciente.html";
    } 
    
    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, @RequestParam String apellido, @RequestParam String dni, 
            @RequestParam String sexo, @RequestParam String domicilio, @RequestParam String localidad,
            @RequestParam String provincia, @RequestParam String telefono, @RequestParam String email, 
            ModelMap modelo) throws MiException{
        
        try {
            pacienteServicio.crearPaciente(nombre, apellido, dni, sexo, domicilio, localidad, provincia, telefono, email);

            modelo.put("exito", "El Paciente fue guardado correctamente");

        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());

            return "formularioPaciente.html";
        }
        return "formularioPaciente.html";
    }
    
    @GetMapping("/lista")
    public String listar(ModelMap modelo) {

        List<Paciente> pacientes = pacienteServicio.listarPacientes();
        modelo.addAttribute("pacientes", pacientes);
        return "pacienteList.html";
    }
    
}
