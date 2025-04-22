
package com.fran.ogascon.servicios;

import com.fran.ogascon.repositorios.ImagenRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fran.ogascon.entidades.Imagen;
import com.fran.ogascon.entidades.Tratamiento;
import com.fran.ogascon.excepciones.MiException;
import jakarta.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImagenServicio {
    
    @Autowired
    private ImagenRepositorio imagenRepositorio;
    
        // Método para validar tipos MIME permitidos
    private boolean validarTipoMIME ( String mimeType) {
        // Lista de tipos MIME permitidos   ----- Para archivos tipo .stl  algunos recomiendan estos tipos de MIME:  "application/sla" ,
        //    "application/vnd.ms-pki.stl ". "application/x-navistyle", otros dicen que con "model/stl" es la denominacion estandar
        List<String> tiposPermitidos = Arrays.asList("image/jpeg", "image/png", "application/pdf", "video/mp4", "model/stl");
        // Verificar si el tipo MIME está en la lista de tipos permitidos
        return tiposPermitidos.contains(mimeType);
    }
    
      @Transactional
    public Imagen guardar(MultipartFile archivo, Tratamiento t) throws MiException {
        System.out.println("guardar imagen1");
        if (archivo != null && validarTipoMIME(archivo.getContentType())) {
            try {
                String extension = ""; 
                String originalFilename = archivo.getOriginalFilename(); 
                Imagen imagen = new Imagen();
                Date date = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
                String formattedDate = dateFormat.format(date);
                if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
}
                imagen.setMime(archivo.getContentType());
                //imagen.setNombre(archivo.getOriginalFilename()); Esta linea esta trasladando el nombre Original con el que se sube la imagen. NO ME SIRVE
                String fileName = String.format("%s_%s_%s_%s%s",
                        t.getPaciente().getApellido(),                        
                        t.getPaciente().getNombre(),
                        archivo.getName(),
                        formattedDate,
                        extension
                );
                //imagen.setNombre(t.getPaciente().getApellido()+t.getPaciente().getNombre()+" "+date.toString());
                imagen.setNombre(fileName);
                imagen.setContenido(archivo.getBytes());
                imagen.setTratamiento(t);
                 System.out.println("guardar ultimo paso");
                return imagenRepositorio.save(imagen);

            } catch (Exception e) {
                System.err.println("error al guardar la imagen: " + e.getMessage());
                
            }
        }
        return null;
    }

    public Imagen actualizar(MultipartFile archivo, String idImagen) throws MiException {
        if (archivo != null && validarTipoMIME(archivo.getContentType())) {
            try {
                Optional<Imagen> respuesta = imagenRepositorio.findById(idImagen);
                if (respuesta.isPresent()) {
                    Imagen imagen = respuesta.get();
                imagen.setMime(archivo.getContentType());
                imagen.setNombre(archivo.getOriginalFilename());
                imagen.setContenido(archivo.getBytes());
                return imagenRepositorio.save(imagen);
                }
            } catch (Exception e) {
                throw new MiException("Error al actualizar la imagen: " + e.getMessage());
            }
        }
        return null;

    }
}
