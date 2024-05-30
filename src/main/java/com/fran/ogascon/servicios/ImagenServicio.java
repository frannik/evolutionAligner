
package com.fran.ogascon.servicios;

import com.fran.ogascon.repositorios.ImagenRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fran.ogascon.entidades.Imagen;
import com.fran.ogascon.entidades.Tratamiento;
import com.fran.ogascon.excepciones.MiException;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Arrays;
import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImagenServicio {
    
    @Autowired
    private ImagenRepositorio imagenRepositorio;
    
        // Método para validar tipos MIME permitidos
    private boolean validarTipoMIME ( String mimeType) {
        // Lista de tipos MIME permitidos
        List<String> tiposPermitidos = Arrays.asList("image/jpeg", "image/png", "image/pdf", "video/mp4");
        // Verificar si el tipo MIME está en la lista de tipos permitidos
        return tiposPermitidos.contains(mimeType);
    }
    
    @Transactional
    public Imagen guardar(MultipartFile archivo, Tratamiento t) throws MiException {
        System.out.println("guardar imagen1");
        if (archivo != null && validarTipoMIME(archivo.getContentType())) {
            try {
                Imagen imagen = new Imagen();
                imagen.setMime(archivo.getContentType());
                imagen.setNombre(archivo.getName());
                imagen.setContenido(archivo.getBytes());
                imagen.setTratamiento(t);
                 System.out.println("guardar ultimo paso");
                return imagenRepositorio.save(imagen);

            } catch (Exception e) {
                System.err.println("error imagen: " + e.getMessage());
                
            }
        }
        return null;
    }

    public Imagen actualizar(MultipartFile archivo, String idImagen) throws MiException {
        if (archivo != null && validarTipoMIME(archivo.getContentType())) {
            try {
                Imagen imagen = new Imagen();

                if (idImagen != null) {
                    Optional<Imagen> respuesta = imagenRepositorio.findById(idImagen);
                    if (respuesta.isPresent()) {
                        imagen = respuesta.get();
                    }
                }
                imagen.setMime(archivo.getContentType());
                imagen.setNombre(archivo.getName());
                imagen.setContenido(archivo.getBytes());

                return imagenRepositorio.save(imagen);

            } catch (Exception e) {
                System.err.println("error imagen: " + e.getMessage());
            }
        }
        return null;

    }
}
