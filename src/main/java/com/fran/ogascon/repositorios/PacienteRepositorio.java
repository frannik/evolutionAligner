
package com.fran.ogascon.repositorios;

import com.fran.ogascon.entidades.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteRepositorio extends JpaRepository<Paciente, String> {
    
}
