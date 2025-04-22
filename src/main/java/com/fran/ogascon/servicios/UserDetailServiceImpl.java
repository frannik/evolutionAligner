
package com.fran.ogascon.servicios;

import com.fran.ogascon.entidades.Usuario;
import com.fran.ogascon.repositorios.UsuarioRepositorio;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.buscarUsuarioPorEmail(email)
                .orElseThrow( () -> new UsernameNotFoundException("El Usuario con el siguiente email " +email+ " no existe!."));
        
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().name());
        
        return new User(email, usuario.getPassword(), Collections.singleton(authority));
    }
        
}
