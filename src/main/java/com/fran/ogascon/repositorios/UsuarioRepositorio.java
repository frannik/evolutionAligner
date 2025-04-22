
package com.fran.ogascon.repositorios;

import com.fran.ogascon.entidades.Usuario;
import com.fran.ogascon.enums.Rol;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, String> {
    
 
    
    @Query("SELECT u FROM Usuario u WHERE u.rol = :rol")
    List<Usuario> listaOdontologos(@Param("rol") Rol rol);

    @Query("SELECT u FROM Usuario u WHERE u.email = :email")
    Usuario buscarPorEmail(@Param("email") String email);

    @Query("SELECT u FROM Usuario u WHERE u.email = :email")
    Optional<Usuario> buscarUsuarioPorEmail(String email);
}
