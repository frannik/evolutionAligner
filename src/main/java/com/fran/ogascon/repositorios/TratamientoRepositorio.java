
package com.fran.ogascon.repositorios;

import com.fran.ogascon.entidades.Tratamiento;
import com.fran.ogascon.enums.TipoTratamiento;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TratamientoRepositorio extends JpaRepository<Tratamiento, String> {
    
    @Query("SELECT t FROM Tratamiento t JOIN t.listaProfesionales u JOIN t.paciente p WHERE p.id = :idPaciente AND u.id = :idUsuario AND t.tipoTratamiento = :tipoTratamiento")
    Optional<Tratamiento> buscarPorPacienteUsuarioYTipo(@Param("idPaciente") String idPaciente, @Param("idUsuario") String idUsuario, @Param("tipoTratamiento") TipoTratamiento tipoTratamiento);
}
