
package com.fran.ogascon.servicios;

import ch.qos.logback.core.util.OptionHelper;
import com.fran.ogascon.repositorios.UsuarioRepositorio;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fran.ogascon.entidades.Usuario;
import com.fran.ogascon.enums.Rol;
import com.fran.ogascon.excepciones.MiException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServicio {
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Transactional 
    public void crearUsuario( String nombre, String apellido,  String dni,  String domicilio,  String localidad,
                                                 String provincia,  String telefono,  String email,  Rol rol,  String password, String password2) throws MiException {
           
        validar(nombre, apellido, dni, domicilio, localidad, provincia, telefono, email, rol, password, password2);
        Usuario u = new Usuario();
        
        u.setNombre(nombre);
        u.setApellido(apellido);
        u.setDni(dni);
        u.setDomicilio(domicilio);
        u.setProvincia(provincia);
        u.setLocalidad(localidad);
        u.setTelefono(telefono);
        u.setEmail(email);
        u.setPassword(password);
        u.setRol(rol);
        u.setAlta(true);
        u.setFechaAlta(new Date());
  
        usuarioRepositorio.save(u);
    }
   
    public List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        usuarios = usuarioRepositorio.findAll();
        return usuarios;
    }
    
    public List<Usuario> listaOdontologos() {
        List<Usuario> listaOdontologos = new ArrayList<>();
        listaOdontologos = usuarioRepositorio.listaOdontologos(Rol.ODONTOLOGO);
        return listaOdontologos;
    }
    
    @Transactional
    public void modificarDatosUsuario( String id, String nombre, String apellido,  String dni,  String domicilio,  String localidad,
                                     String provincia,  String telefono,  String email,  Rol rol,  String password, 
                                     String password2) throws MiException {
        
        validar(nombre, apellido, dni, domicilio, localidad, provincia, telefono, email, rol, password, password2);
        
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Usuario p = respuesta.get();
            p.setNombre(nombre);
            p.setApellido(apellido);
            p.setDni(dni);
            p.setRol(rol);
            p.setDomicilio(domicilio);
            p.setTelefono(telefono);
            p.setEmail(email);
            if (password == password2) {
                p.setPassword(password);
            }
            usuarioRepositorio.save(p);
        }
    }
    // El metodo a conticuacion, sirve para buscar un Usuario por un Id que viene de Interf Graf.
    public Usuario getOne(String id){ 
        return usuarioRepositorio.getOne(id);
    }
    
    @Transactional
    public void eliminarUsuario( String id) {
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);       
        if (respuesta.isPresent()) {
            Usuario u = respuesta.get();
            if(u.isAlta()){
            u.setAlta(false);
            System.out.println("paso1");
            u.setFechaBaja(new Date());
            }else {
            u.setAlta(true);
            u.setFechaBaja(null);
            u.setFechaAlta(new Date());
            usuarioRepositorio.save(u);
        }
    }
    }
    private void validar( String nombre, String apellido,  String dni,  String domicilio,  String localidad,
                                     String provincia,  String telefono,  String email,  Rol rol,  String password, 
                                     String password2) throws MiException {
        
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
        if (rol.toString().isEmpty() || email ==null ) {
            throw new MiException("El Rol debe ser asignado");
        }
        if ( password.isEmpty() || password ==null) {
            throw new MiException("La password no puede ser nula ni vacia");
        }
        if ( password2.isEmpty() || password2 ==null) {
            throw new MiException("La password no puede ser nula ni vacia");
        }
        if ( !password.equals(password2)) {
            throw new MiException("Las password no son coincidentes");
        }
    }
}


