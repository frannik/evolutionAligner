
package com.fran.ogascon.entidades;

import com.fran.ogascon.enums.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Paciente {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String nombre;
    private String apellido;
    private String dni; 
    private String sexo;
    private String domicilio;
    private String localidad; 
    private String provincia; 
    private String telefono;
    private String email;
    
   
    private Status status;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAlta;
    private boolean alta; 
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaBaja;
    
    @OneToMany(mappedBy = "paciente")
    private List<Tratamiento> tratamientos;
    

}


