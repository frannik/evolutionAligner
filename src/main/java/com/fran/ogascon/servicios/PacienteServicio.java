package com.fran.ogascon.servicios;

import com.fran.ogascon.entidades.Tratamiento;
import com.fran.ogascon.repositorios.PacienteRepositorio;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fran.ogascon.entidades.Paciente;
import com.fran.ogascon.enums.Status;
import com.fran.ogascon.enums.TipoTratamiento;
import com.fran.ogascon.excepciones.MiException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PacienteServicio {

    @Autowired
    private PacienteRepositorio pacienteRepositorio;

    @Transactional
    public void crearPaciente(String nombre,String apellido, String dni, String sexo, String domicilio, String localidad, String provincia, String telefono, String email) throws MiException {
        validar(nombre, apellido, dni, domicilio, localidad, provincia, telefono, email);
        Paciente p = new Paciente();
        
        p.setNombre(nombre);
        p.setApellido(apellido);
        p.setDni(dni);
        p.setSexo(sexo);
        p.setDomicilio(domicilio);
        p.setLocalidad(localidad);
        p.setProvincia(provincia);
        p.setTelefono(telefono);
        p.setEmail(email);
        p.setStatus(Status.Pendiente);
        p.setAlta(true);
        p.setFechaAlta(new Date());
        pacienteRepositorio.save(p);
    }
    
    public List<Paciente> listarPacientes() {
        List<Paciente> pacientes = new ArrayList();
        pacientes = pacienteRepositorio.findAll();
        return pacientes;
    }
    
    // Pensar que deberia usar un metodo para Modificar Datos Personales
    //otro para Modificar Status
    // otros para Modificar Alta
    // otro para modificar Tratamiento
    @Transactional
    public void modificarDatosPaciente(@Valid String id, @Valid String nombre,@Valid String apellido, @Valid String dni, 
                                                   @Valid String domicilio, @Valid String telefono, @Valid String email ){
        Optional<Paciente> respuesta=pacienteRepositorio.findById(id);
        if (respuesta.isPresent()) {
                Paciente p = respuesta.get();
                p.setNombre(nombre);
                p.setApellido(apellido);
                p.setDni(dni);
                p.setDomicilio(domicilio);
                p.setTelefono(telefono);
                p.setEmail(email);
                
                pacienteRepositorio.save(p);
        }
       
    }
    
    @Transactional
    public void eliminarPaciente(@Valid String id){
         Optional<Paciente> respuesta=pacienteRepositorio.findById(id);
        if (respuesta.isPresent()) {
            
            Paciente p = new Paciente();
            
            p.setAlta(false);
            p.setFechaBaja(new Date());
            pacienteRepositorio.save(p);            
        }
    }
    
    @Transactional
    public void modificarStatusPaciente(@Valid String id, Enum<Status> status){
         Optional<Paciente> respuesta=pacienteRepositorio.findById(id);
        if (respuesta.isPresent()) {
            
            Paciente p = new Paciente();
            
            //p.setStatus();   REVISAR COMO PONER  UN OPCIONAL ACA
            
            pacienteRepositorio.save(p);            
        }
    }
    
    private void validar( String nombre,String apellido, String dni, String domicilio,String localidad,
                                     String provincia, String telefono, String email) throws MiException {
        
        if ( nombre.isEmpty() || nombre ==null) {
            throw new MiException("El nombre no puede ser nulo ni vacio");
        }
        if ( apellido.isEmpty() || apellido ==null) {
            throw new MiException("El apellido no puede ser nulo ni vacio");
        }
        if ( dni.isEmpty() || dni ==null) {
            throw new MiException("El dni no puede ser nulo ni vacio");
        }
        if ( domicilio.isEmpty() || domicilio ==null) {
            throw new MiException("El domicilio no puede ser nulo ni vacio");
        }
        if ( localidad.isEmpty() || localidad ==null) {
            throw new MiException("La localidad no puede ser nula ni vacia");
        }
        if ( provincia.isEmpty() || provincia ==null) {
            throw new MiException("La provincia no puede ser nula ni vacia");
        }
        if ( telefono.isEmpty() || telefono ==null) {
            throw new MiException("El telefono no puede ser nulo ni vacio");
        }
        if ( email.isEmpty() || email ==null) {
            throw new MiException("El email no puede ser nulo ni vacio");
        }
        
    }
 
        
    
}
    
