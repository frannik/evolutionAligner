
package com.fran.ogascon.servicios;

import com.fran.ogascon.repositorios.UsuarioRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fran.ogascon.entidades.Usuario;
import com.fran.ogascon.enums.Rol;
import com.fran.ogascon.excepciones.MiException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UsuarioServicio implements UserDetailsService{
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private PasswordEncoder passwordEncoder;
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
        u.setPassword(passwordEncoder.encode(password));
        u.setRol(rol);
        u.setAlta(true);
        u.setAccountNonExpired(true);
        u.setAccountNonLocked(true);
        u.setCredentialsNonExpired(true);
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
                p.setPassword(new BCryptPasswordEncoder().encode(password));
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
        if ( password.isEmpty() || password ==null || password.length()<=5) {
            throw new MiException("La contraseña no puede estar vacia, y debe tener más de 5 dígitos");
        }
        if ( !password.equals(password2)) {
            throw new MiException("Las contraseñas deben ser iguales");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.buscarPorEmail(email);
        
        if (usuario != null){
            List<GrantedAuthority> permisos = new ArrayList();
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_"+ usuario.getRol().toString());
            permisos.add(p);
            return new User(usuario.getEmail(), usuario.getPassword(),permisos);
        }else{
            return null;
        }                    
    }
    
    
}


