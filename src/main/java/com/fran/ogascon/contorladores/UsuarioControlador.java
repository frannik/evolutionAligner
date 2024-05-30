package com.fran.ogascon.contorladores;

import com.fran.ogascon.enums.Rol;
import com.fran.ogascon.entidades.Usuario;
import com.fran.ogascon.excepciones.MiException;
import com.fran.ogascon.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/usuario")
public class UsuarioControlador {
    
    @Autowired
    private UsuarioServicio usuarioServicio;
   
    
    @GetMapping("/registrar")
    public String registrar(){
        return "formularioUsuario.html";
    } 
    
    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, @RequestParam String apellido, @RequestParam String dni,
            @RequestParam String rolString, @RequestParam String domicilio, @RequestParam String localidad,
            @RequestParam String provincia, @RequestParam String telefono, @RequestParam String email,
            @RequestParam String password, @RequestParam String password2, ModelMap modelo) {
        try {
            if (rolString.isEmpty()) {
                throw new MiException("Debe seleccionar un rol");
            }

            Rol rol = Rol.valueOf(rolString); // Convertir el string del rol a Enum Rol

            usuarioServicio.crearUsuario(nombre, apellido, dni, domicilio, localidad, provincia, telefono, email, rol, password, password2);

            modelo.put("exito", "El Usuario fue guardado correctamente");

        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());

            return "formularioUsuario.html";
        }

        return "formularioUsuario.html";
    }
    
    @GetMapping("/lista")
    public String listar(ModelMap modelo){
        
        List<Usuario> usuarios = usuarioServicio.listarUsuarios();
        modelo.addAttribute("usuarios", usuarios);
        return "usuarioList.html";
    }
    
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo){
        modelo.put("usuario", usuarioServicio.getOne(id));
        return "usuarioModificar.html";
    }

    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String dni,
            @RequestParam String rolString, @RequestParam String domicilio, @RequestParam String localidad,
            @RequestParam String provincia, @RequestParam String telefono, @RequestParam String email,
            @RequestParam String password, @RequestParam String password2, ModelMap modelo) {
        try {
            Rol rol = Rol.valueOf(rolString);
            usuarioServicio.modificarDatosUsuario(id, nombre, apellido, dni, domicilio, localidad, provincia, telefono, email, rol, password, password2);
            modelo.addAttribute("exito", "El Usuario fue modificado correctamente.");

        } catch (MiException ex) {
            modelo.addAttribute("error", ex.getMessage());
            return "usuarioModificar.html";
        }
        return "redirect:../lista";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id, ModelMap modelo) {
        usuarioServicio.eliminarUsuario(id);
        
        return "redirect:../lista";
    }  
    
    @GetMapping("/info/{id}")
    public String info(@PathVariable String id, ModelMap modelo) {
        modelo.put("usuario", usuarioServicio.getOne(id));
        
        return "usuarioVista.html";
    }  
    

    
}