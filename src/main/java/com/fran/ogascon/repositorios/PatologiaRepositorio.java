
package com.fran.ogascon.repositorios;

import com.fran.ogascon.entidades.Patologia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatologiaRepositorio extends JpaRepository<Patologia, String> {
    
}