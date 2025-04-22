
package com.fran.ogascon.servicios;

import com.fran.ogascon.entidades.Patologia;
import com.fran.ogascon.repositorios.PatologiaRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class PatologiaServicio {
   

    @Autowired
    private PatologiaRepositorio patologiaRepository;

    public List<Patologia> listarPatologias() {
        return patologiaRepository.findAll();
    }

    public Optional<Patologia> buscarPatologiaPorId(String id) {
        return patologiaRepository.findById(id);
    }

    public Patologia guardarPatologia(Patologia patologia) {
        return patologiaRepository.save(patologia);
    }

    public Patologia actualizarPatologia(String id, Patologia patologiaDetalles) {
        return patologiaRepository.findById(id).map(patologia -> {
            patologia.setNombre(patologiaDetalles.getNombre());
            return patologiaRepository.save(patologia);
        }).orElseThrow(() -> new RuntimeException("Patología no encontrada"));
    }

    public void eliminarPatologia(String id) {
        patologiaRepository.deleteById(id);
    }
}

