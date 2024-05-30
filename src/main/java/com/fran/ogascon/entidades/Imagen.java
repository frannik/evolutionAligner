
package com.fran.ogascon.entidades;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Imagen {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String mime;  //asigna el formato del archivo de la imagen
    private String nombre;
    
    @ManyToOne
    @JoinColumn(name = "tratamiento_id")
    private Tratamiento tratamiento;
        
    @Lob
    @Column( columnDefinition="blob")
    @Basic(fetch=FetchType.EAGER)
    private byte[] contenido;  //arreglo de bytes en el que se guarda el contenido. es archivo grande.  LAZY se carga solo cuando se hace un Get.

}
