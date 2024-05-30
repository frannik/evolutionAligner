 
package com.fran.ogascon.entidades;

import com.fran.ogascon.enums.Patologia;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.hibernate.annotations.GenericGenerator;
import com.fran.ogascon.enums.Status;
import com.fran.ogascon.enums.TipoTratamiento;
import jakarta.persistence.CascadeType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Tratamiento {
  
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    
    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;
   
    @ManyToMany
    @JoinTable(name = "tratamiento_usuario",
               joinColumns = @JoinColumn(name = "tratamiento_id"),
               inverseJoinColumns = @JoinColumn(name = "usuario_id"))
    private List<Usuario> listaProfesionales;
    
    @Enumerated(EnumType.STRING)
    private Patologia patologia;
    
    @Enumerated(EnumType.STRING)
    private Status statusOdontologo;
    @Enumerated(EnumType.STRING)
    private Status statusOrtodoncita;
    @Enumerated(EnumType.STRING)
    private Status statusTecnico;
    @Enumerated(EnumType.STRING)
    private TipoTratamiento tipoTratamiento; 
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAltaOdontologo;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAltaOrtodoncista;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAltaTecnico;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaBaja;
    
    @OneToMany(mappedBy = "tratamiento", cascade = {CascadeType.PERSIST})
    private List<Imagen> imagenesPaciente;
    
    private String prioridadEsteticaPaciente; // observacion que explaya l odontologo especificamente a este campo.
    private String corregirLineaMedia; //SI o NO
    private List<String> lineaMediaSup; // son palabras preestablecidas que son de seleccion multiple. Ver de hacerlo con una Lista.
    private List<String>lineaMediaInf; // son palabras preestablecidas que son de seleccion multiple. Ver de hacerlo con una Lista. 
    private String espacios;
    private String overjet; // son palabras preestablecidas que son de seleccion multiple. Ver de hacerlo con una Lista.
    private String overbite; // son palabras preestablecidas que son de seleccion multiple. Ver de hacerlo con una Lista.
    
    private List<String> discrepanciaEspacios; // son palabras preestablecidas que son de seleccion multiple. Ver de hacerlo con una Lista. Las opciones son: Ninugno, Superior Mantener, Superior Cerrar, Inferior Mantener, Inferior Cerrar.
    private List<String> apinamiento; // tiene opciones: Ninguno, Superior Expandir, Superior Proclinar, Superior Stripping, Inferior Expandir, Inferior Proclinar, Inferior Stripping
    private List<String> correccionSentidoTransversal; // son palabras preestablecidas que son de seleccion multiple. Ver de hacerlo con una Lista. Tiene opciones: Ninguno, Superior Expandir, Superior Proclinar, Superior Stripping, Inferior Expandir, Inferior Proclinar, Inferior Stripping
    private String confirmarAtachments; // cuadro de texto
    private String attachments; // cuadro de texto   
    private String observaciones; // cuadro de texto
    
    private boolean textoLegal; // aca el odontologo debe aceptar las condiciones legales. 
}
