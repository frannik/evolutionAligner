
package com.fran.ogascon.contorladores;

import com.fran.ogascon.enums.Patologia;
import com.fran.ogascon.entidades.Paciente;
import com.fran.ogascon.entidades.Usuario;
import com.fran.ogascon.entidades.Tratamiento;
import com.fran.ogascon.enums.Rol;
import com.fran.ogascon.enums.TipoTratamiento;
import com.fran.ogascon.excepciones.MiException;
import com.fran.ogascon.servicios.PacienteServicio;
import com.fran.ogascon.servicios.TratamientoServicio;
import com.fran.ogascon.servicios.UsuarioServicio;
import java.io.IOException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@Slf4j
@Component
@RequestMapping("/tratamiento")
public class TratamientoControlador {
    
    @Autowired
    private TratamientoServicio tratamientoServicio;
    @Autowired
    private PacienteServicio pacienteServicio;
    @Autowired
    private UsuarioServicio usuarioServicio;
    
    
    @GetMapping("/registrar")
    public String registrar(ModelMap modelo, ModelMap modelo1){
        List<Paciente> listaPacientes = pacienteServicio.listarPacientes();
        modelo.put("pacientes", listaPacientes);
        
        List<Usuario> listaUsuarios = usuarioServicio.listaOdontologos();
        modelo.put("odontologos", listaUsuarios);
        
        return "formularioTratamiento.html";
    } 

    @PostMapping("/registro")
    public String resgistro(@RequestParam("idPaciente") String idPaciente,
                                   @RequestParam(required = false) Patologia patologia,
                                   @RequestParam("idUsuario") String idUsuario,
                                   @RequestParam("tipoTratamiento") TipoTratamiento tipoTratamiento,                                   
                                   @RequestParam(required = false) MultipartFile rxPanoramica,
                                   @RequestParam(required = false) MultipartFile lateralDer, 
                                   @RequestParam(required = false) MultipartFile lateralIzq,  
                                   @RequestParam(required = false) MultipartFile inoclusion,
                                   @RequestParam(required = false) MultipartFile oclusal, 
                                   @RequestParam(required = false) MultipartFile oclusalSup, 
                                   @RequestParam(required = false) MultipartFile oclusalInf,
                                   @RequestParam(required = false) MultipartFile teleradiografia,
                                   @RequestParam(required = false) MultipartFile topografiaConeBean,
                                   @RequestParam(required = false) MultipartFile imagenFrente,
                                   @RequestParam(required = false) MultipartFile imagenFrenteSonrisa, 
                                   @RequestParam(required = false) MultipartFile imagenPerfil, 
                                   @RequestParam(required = false) MultipartFile imagenPerfilConProyeccion, 
                                   @RequestParam(required = false) MultipartFile videoMotivoDeConsulta, 
                                   @RequestParam(required = false) MultipartFile estudioMcNamara, 
                                   @RequestParam(required = false) MultipartFile estudioRickets, 
                                   @RequestParam(required = false) MultipartFile odontograma,
                                   @RequestParam(required = false) String prioridadEsteticaPaciente, 
                                   @RequestParam(required = false) String corregirLineaMedia, 
                                   @RequestParam(required = false) List<String> lineaMediaSup, 
                                   @RequestParam(required = false) List<String> lineaMediaInf, 
                                   @RequestParam(required = false) String espacios, 
                                   @RequestParam(required = false) String overjet, 
                                   @RequestParam(required = false) String overbite, 
                                   @RequestParam(required = false) List<String> discrepanciaEspacios, 
                                   @RequestParam(required = false) List<String> apinamiento, 
                                   @RequestParam(required = false) List<String> correccionSentidoTransversal, 
                                   @RequestParam(required = false) String confirmarAtachments,
                                   @RequestParam(required = false) String attachments,
                                   @RequestParam(required = false) String observaciones,
                                   @RequestParam("aceptar") boolean aceptar,
                                   ModelMap modelo) throws MiException, IOException{
        
        try {
            
            
            Tratamiento t = tratamientoServicio.crearTratamiento(idPaciente, patologia, idUsuario, tipoTratamiento, aceptar);
            tratamientoServicio.documentacionTratamientoOdontologo(idUsuario, t.getId(), rxPanoramica,
                    lateralDer, lateralIzq, inoclusion, oclusal, oclusalSup, oclusalInf, teleradiografia, topografiaConeBean,
                    imagenFrente, imagenFrenteSonrisa, imagenPerfil, imagenPerfilConProyeccion, videoMotivoDeConsulta,
                    estudioMcNamara, estudioRickets, odontograma, prioridadEsteticaPaciente, corregirLineaMedia,
                    lineaMediaSup, lineaMediaInf, espacios, overjet, overbite, discrepanciaEspacios, apinamiento,
                    correccionSentidoTransversal, confirmarAtachments, attachments, observaciones);
          
            modelo.put("exito", "Se agregó el Tratamiento con Exito!");
      
        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
            return "formularioTratamiento.html";
        }     
        return "formularioTratamiento.html";
    }
    
    @GetMapping("/lista")
    public String listar(ModelMap modelo) {
        List<Tratamiento> tratamientos = tratamientoServicio.listarTratamiento();
        modelo.addAttribute("tratamientos", tratamientos);
        return "tratamientoList.html";
    }
    
    public String obtenerNombreOdontologo(List<Usuario> listaProfesionales) {
        for (Usuario usuario : listaProfesionales) {
            if (usuario.getRol().equals(Rol.ODONTOLOGO)) {
                return usuario.getApellido() + usuario.getNombre();
            }
        }
        return "Sin Asignación";
    }
   
    public String obtenerNombreOrtodoncista(List<Usuario> listaProfesionales) {
        for (Usuario usuario : listaProfesionales) {
            if (usuario.getRol().equals(Rol.ORTODONCISTA)) {
                return usuario.getApellido() + usuario.getNombre();
            }
        }
        return "Sin Asignación";
    }
    
    public String obtenerNombreTecnico(List<Usuario> listaProfesionales) {
        for (Usuario usuario : listaProfesionales) {
            if (usuario.getRol().equals(Rol.TECNICO)) {
                return usuario.getApellido() + usuario.getNombre();
            }
        }
        return "Sin Asignación";
    }
    
        @GetMapping("/ortodoncista")
    public String ortodoncista(){
        return "formularioOrtodoncista.html";
    } 
    
    @PostMapping("/ortodon")
    public String ortodon(String idUsuario, String idTratamiento){
        tratamientoServicio.aceptarTratamientoOrtodoncista(idUsuario, idTratamiento);
        return "tratamientoList.html";
    }
    
           @GetMapping("/tecnico")
    public String tecnico(){
        return "formularioTecnico.html";
    } 
    
    @PostMapping("/tecnic")
    public String tecnic(String idUsuario, String idTratamiento){
        tratamientoServicio.aceptarTratamientoTecnico(idUsuario, idTratamiento);
        return "tratamientoList.html";
    }
}