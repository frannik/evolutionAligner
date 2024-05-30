
package com.fran.ogascon.servicios;

import com.fran.ogascon.enums.TipoTratamiento;
import com.fran.ogascon.entidades.Tratamiento;
import com.fran.ogascon.entidades.Paciente;
import com.fran.ogascon.entidades.Imagen;
import com.fran.ogascon.entidades.Usuario;
import com.fran.ogascon.enums.Patologia;
import com.fran.ogascon.enums.Status;
import com.fran.ogascon.excepciones.MiException;
import com.fran.ogascon.repositorios.PacienteRepositorio;
import com.fran.ogascon.repositorios.TratamientoRepositorio;
import com.fran.ogascon.repositorios.UsuarioRepositorio;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class TratamientoServicio {

    @Autowired
    private TratamientoRepositorio tratamientoRepositorio;
    @Autowired
    private PacienteRepositorio pacienteRepositorio;
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private ImagenServicio imagenServicio;

    @Transactional
    public Tratamiento crearTratamiento (String idPaciente, Patologia patologia, String idUsuario, TipoTratamiento tipoTratamiento, 
                                                    boolean aceptar) throws MiException{
        
        validarTratamiento(idPaciente, idUsuario, patologia, tipoTratamiento, aceptar);
        Paciente p = pacienteRepositorio.findById(idPaciente).get();
        Usuario u = usuarioRepositorio.findById(idUsuario).get();
        Tratamiento t = new Tratamiento();
        t.setPatologia(patologia);
        t.setStatusOdontologo(Status.Aceptado);
        t.setFechaAltaOdontologo(new Date());
        t.setTipoTratamiento(tipoTratamiento);
        t.setPaciente(p);
        List<Usuario> lista = new ArrayList();
        lista.add(u);
        t.setListaProfesionales(lista);
        t.setTextoLegal(aceptar);
        return tratamientoRepositorio.save(t);
    }

    @Transactional
    public void aceptarTratamientoOrtodoncista(@Valid String idUsuario, @Valid String idTratamiento) {
        Optional<Tratamiento> respuesta = tratamientoRepositorio.findById(idTratamiento);
        Optional<Usuario> respuesta1 = usuarioRepositorio.findById(idUsuario);
        if (respuesta.isPresent() && respuesta1.isPresent()) {
            Tratamiento t = respuesta.get();
            Usuario u = respuesta1.get(); //Esto tiene que servir para que envie correos este Metodo.

            t.setStatusOrtodoncita(Status.Aceptado);
            t.setFechaAltaOrtodoncista(new Date());
             tratamientoRepositorio.save(t);
        }
    }

    @Transactional
    public void aceptarTratamientoTecnico(@Valid String idUsuario, @Valid String idTratamiento) {
        Optional<Tratamiento> respuesta = tratamientoRepositorio.findById(idTratamiento);
        Optional<Usuario> respuesta1 = usuarioRepositorio.findById(idUsuario);
        if (respuesta.isPresent() && respuesta1.isPresent()) {
            Tratamiento t = respuesta.get();
            Usuario u = respuesta1.get(); //Esto tiene que servir para que envie correos este Metodo.

            t.setStatusTecnico(Status.Aceptado);
            t.setFechaAltaTecnico(new Date());
            tratamientoRepositorio.save(t);
        }
    }

    @Transactional
    public void documentacionTratamientoOdontologo(String idUsuario, String idTratamiento,
            MultipartFile rxPanoramica,
            MultipartFile lateralDer, MultipartFile lateralIzq, MultipartFile inoclusion,
            MultipartFile oclusal, MultipartFile oclusalSup, MultipartFile oclusalInf,
            MultipartFile teleradiografia, MultipartFile topografiaConeBean,
            MultipartFile imagenFrente, MultipartFile imagenFrenteSonrisa, MultipartFile imagenPerfil,
            MultipartFile imagenPerfilConProyeccion, MultipartFile videoMotivoDeConsulta,
            MultipartFile estudioMcNamara, MultipartFile estudioRickets, MultipartFile odontograma,
            String prioridadEsteticaPaciente, String corregirLineaMedia, List<String> lineaMediaSup,
            List<String> lineaMediaInf, String espacios, String overjet, String overbite,
            List<String> discrepanciaEspacios, List<String> apinamiento, List<String> correccionSentidoTransversal,
            String confirmarAtachments, String attachments, String observaciones) throws MiException, IOException {

        Optional<Tratamiento> respuesta = tratamientoRepositorio.findById(idTratamiento);
        if (respuesta.isPresent()) {
            Tratamiento t = respuesta.get();
            List<MultipartFile> archivos = Arrays.asList(rxPanoramica, lateralDer, lateralIzq, inoclusion, oclusal, oclusalSup,
                    oclusalInf, teleradiografia, topografiaConeBean, imagenFrente, imagenFrenteSonrisa, imagenPerfil,
                    imagenPerfilConProyeccion, videoMotivoDeConsulta, estudioMcNamara, estudioRickets, odontograma);
            List<Imagen> imagenes = new ArrayList<>();
            for (MultipartFile archivo : archivos) {
                Imagen imagen = imagenServicio.guardar(archivo, t);
                imagenes.add(imagen);
            }
            t.setImagenesPaciente(imagenes);
//             Imagen imagen = new Imagen();
//                imagen.setMime(rxPanoramica.getContentType());
//                imagen.setNombre(rxPanoramica.getName());
//                imagen.setContenido(rxPanoramica.getBytes());
//                 imagen.setTratamiento(t);

            //VALIDAR Y MENSAJES DE NULL
//             listado.add(imagen);
////----------------------------------------------------
//             List<Imagen> listado = new ArrayList();
//             MultipartFile archivo = rxPanoramica;
//             Imagen imagen = imagenServicio.guardar(archivo, t);
//             listado.add(imagen);
//             t.setImagenesPaciente(listado);
//             MultipartFile archivo1 = lateralDer;
//             Imagen imagen1 = imagenServicio.guardar(archivo1, t);
//             listado.add(imagen1);
//             t.setImagenesPaciente(listado);
//             MultipartFile archivo2 = lateralIzq;
//             Imagen imagen2 = imagenServicio.guardar(archivo2, t);
//             listado.add(imagen2);
//             t.setImagenesPaciente(listado);
//             MultipartFile archivo3 = inoclusion;
//             Imagen imagen3 = imagenServicio.guardar(archivo3, t);
//             listado.add(imagen3);
//             t.setImagenesPaciente(listado);
//             MultipartFile archivo4 = oclusal;
//             Imagen imagen4 = imagenServicio.guardar(archivo4, t);
//             listado.add(imagen4);
//             t.setImagenesPaciente(listado);
//             MultipartFile archivo5 = oclusalSup;
//             Imagen imagen5 = imagenServicio.guardar(archivo5, t);
//             listado.add(imagen5);
//             t.setImagenesPaciente(listado);
//             MultipartFile archivo6 = oclusalInf;
//             Imagen imagen6 = imagenServicio.guardar(archivo6, t);
//             listado.add(imagen6);
//             t.setImagenesPaciente(listado);
//             MultipartFile archivo7 = teleradiografia;
//             Imagen imagen7 = imagenServicio.guardar(archivo7, t);
//             listado.add(imagen7);
//             t.setImagenesPaciente(listado);
//             MultipartFile archivo8 = topografiaConeBean;
//             Imagen imagen8 = imagenServicio.guardar(archivo8, t);
//             listado.add(imagen8);
//             t.setImagenesPaciente(listado);
//             MultipartFile archivo9 = imagenFrente;
//             Imagen imagen9 = imagenServicio.guardar(archivo9, t);
//             listado.add(imagen9);
//             t.setImagenesPaciente(listado);
//             MultipartFile archivo10 = imagenFrenteSonrisa;
//             Imagen imagen10 = imagenServicio.guardar(archivo10, t);
//             listado.add(imagen10);
//             t.setImagenesPaciente(listado);
//             MultipartFile archivo11 = imagenPerfil;
//             Imagen imagen11 = imagenServicio.guardar(archivo11, t);
//             listado.add(imagen11);
//             t.setImagenesPaciente(listado);
//             MultipartFile archivo12 = imagenPerfilConProyeccion;
//             Imagen imagen12 = imagenServicio.guardar(archivo12, t);
//             listado.add(imagen12);
//             t.setImagenesPaciente(listado);
//             MultipartFile archivo13 = videoMotivoDeConsulta;
//             Imagen imagen13 = imagenServicio.guardar(archivo13, t);
//             listado.add(imagen13);
//             t.setImagenesPaciente(listado);
//             MultipartFile archivo14 = estudioMcNamara;
//             Imagen imagen14 = imagenServicio.guardar(archivo14, t);
//             listado.add(imagen14);
//             t.setImagenesPaciente(listado);
//             MultipartFile archivo15 = estudioRickets;
//             Imagen imagen15 = imagenServicio.guardar(archivo15, t);
//             listado.add(imagen15);
//             t.setImagenesPaciente(listado);
//             MultipartFile archivo16 = odontograma;
//             Imagen imagen16 = imagenServicio.guardar(archivo16, t);
//             listado.add(imagen16);
//             t.setImagenesPaciente(listado);
            t.setPrioridadEsteticaPaciente(prioridadEsteticaPaciente);
            t.setCorregirLineaMedia(corregirLineaMedia);
            t.setLineaMediaSup(lineaMediaSup);
            t.setLineaMediaInf(lineaMediaInf);
            t.setEspacios(espacios);
            t.setOverjet(overjet);
            t.setOverbite(overbite);

            t.setDiscrepanciaEspacios(discrepanciaEspacios);
            t.setApinamiento(apinamiento);
            t.setCorreccionSentidoTransversal(correccionSentidoTransversal);
            t.setConfirmarAtachments(confirmarAtachments);
            t.setAttachments(attachments);
            t.setObservaciones(observaciones);

            tratamientoRepositorio.save(t);
        }
    }

    public List<Tratamiento> listarTratamiento() {
        List<Tratamiento> tratamientos = new ArrayList();
        tratamientos = tratamientoRepositorio.findAll();
        return tratamientos;
    }
   
    public String buscarIdTratamientoPorPacienteUsuaruioTipoTratam(String idPaciente, String idUsuario, TipoTratamiento tipoTratamiento) {
        Optional<Tratamiento> tratamiento = tratamientoRepositorio.buscarPorPacienteUsuarioYTipo(idPaciente, idUsuario, tipoTratamiento);
        // Verificar si se encontró el tratamiento
        if (tratamiento.isPresent()) {
            return tratamiento.get().getId();
        } else {
            // Manejar el caso en el que no se encuentre el tratamiento
            return null; // o lanzar una excepción, dependiendo de tus requerimientos
        }
    }
    
    private void validarTratamiento(String idPaciente, String idUsuario, Patologia patologia, TipoTratamiento tipoTratamiento,
                                boolean aceptar) throws MiException {

        if (idPaciente.isEmpty() || idPaciente == null) {
            throw new MiException("El Paciente no puede ser nulo ni vacio");
        }    
             if (idUsuario.isEmpty() || idUsuario == null) {
            throw new MiException("El Usuario no puede ser nulo ni vacio");
        }
        if (patologia == null) {
            throw new MiException("La Patologia no puede ser nula ni vacia");
        }
        if (tipoTratamiento == null) {
            throw new MiException("El Tipo de Tratamiento no puede ser nulo ni vacio");
        }
        if (aceptar != true) {
            throw new MiException("Debe Aceptar los terminos legales");
        }

    }
    

    
//    public String obtenerNombreOdontologo(List<Usuario> listaProfesionales) {
//        for (Usuario usuario : listaProfesionales) {
//            if (usuario.getRol().equals(Rol.ODONTOLOGO)) {
//                return usuario.getApellido() + usuario.getNombre();
//            }
//        }
//        return "Sin Asignación";
//    }
//    
//    public String obtenerNombreOrtodoncista(List<Usuario> listaProfesionales) {
//        for (Usuario usuario : listaProfesionales) {
//            if (usuario.getRol().equals(Rol.ORTODONCISTA)) {
//                return usuario.getApellido() + usuario.getNombre();
//            }
//        }
//        return "Sin Asignación";
//    }
//    
//    public String obtenerNombreTecnico(List<Usuario> listaProfesionales) {
//        for (Usuario usuario : listaProfesionales) {
//            if (usuario.getRol().equals(Rol.TECNICO)) {
//                return usuario.getApellido() + usuario.getNombre();
//            }
//        }
//        return "Sin Asignación";
//    }
    
    
}
  
           
           

