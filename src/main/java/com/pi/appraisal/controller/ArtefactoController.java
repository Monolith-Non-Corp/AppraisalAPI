package com.pi.appraisal.controller;

import com.pi.appraisal.component.SessionCache;
import com.pi.appraisal.entity.Artefacto;
import com.pi.appraisal.repository.ArtefactoRepository;
import com.pi.appraisal.repository.EvidenciaRepository;
import com.pi.appraisal.util.Credentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.pi.appraisal.entity.UsuarioRol.Priviledge.ORGANIZACION;

@RestController
@RequestMapping("api/file")
public class ArtefactoController {

    private final ArtefactoRepository artefactoRepository;
    private final EvidenciaRepository evidenciaRepository;
    private final SessionCache session;

    @Autowired
    public ArtefactoController(ArtefactoRepository artefactoRepository, EvidenciaRepository evidenciaRepository, SessionCache session) {
        this.artefactoRepository = artefactoRepository;
        this.evidenciaRepository = evidenciaRepository;
        this.session = session;
    }

    /**
     * Recibe un {@param file} y lo almacena en la evidencia especificada en el {@param evidenciaIn}
     *
     * @param evidenciaIn El ID de una {@link com.pi.appraisal.entity.Evidencia}
     * @param file        Un archivo de cualquier tipo en forma {@link MultipartFile}
     * @param credentials Las {@link com.pi.appraisal.util.Credentials} de la sesion
     * @return El {@link com.pi.appraisal.entity.AreaProceso} creado si es aplicable
     */
    @PostMapping("{evidencia}")
    public ResponseEntity<Artefacto> upload(@PathVariable("evidencia") Integer evidenciaIn,
                                            @RequestParam("file") MultipartFile file,
                                            @RequestHeader("Credentials") Credentials credentials) {
        return session.authenticate(credentials, ORGANIZACION)                                                          //Valida las credenciales
                .map(usuario -> evidenciaRepository.findByUsuario(evidenciaIn, usuario))                                //Si es valido, buscar la evidencia con el usuario
                .map(evidencia -> {
                    if (file.isEmpty()) {                                                                               //Si el archivo esta vacio
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).<Artefacto>build();                      //Enviar error
                    }
                    byte[] bytes;
                    try {
                        bytes = file.getBytes();
                    } catch (IOException e) {                                                                           //Si existe un error al leer el archivo
                        e.printStackTrace();                                                                            //Imprimir error en consola
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).<Artefacto>build();                      //Enviar error
                    }
                    Artefacto artefacto = new Artefacto();                                                              //Crear artefacto
                    artefacto.setArchivo(bytes);
                    artefacto.setNombre(file.getOriginalFilename());
                    artefacto.setTipo(file.getContentType());
                    artefacto.setEvidencia(evidencia);
                    return ResponseEntity.ok(artefactoRepository.save(artefacto));                                      //Guardar artefacto
                }).orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());                                      //Si no es valido, enviar error
    }

    /**
     * Elimina el artefacto especificado en {@param artefactoIn}
     *
     * @param artefactoIn El ID de un {@link com.pi.appraisal.entity.Artefacto}
     * @param credentials Las {@link Credentials} de la sesion
     * @return El {@link com.pi.appraisal.entity.Artefacto} elminado si es aplicable
     */
    @DeleteMapping("{artefacto}")
    public ResponseEntity<Artefacto> delete(@PathVariable("artefacto") Integer artefactoIn,
                                            @RequestHeader("Credentials") Credentials credentials) {
        return session.authenticate(credentials, ORGANIZACION)                                                          //Valida las credenciales
                .map(usuario -> artefactoRepository.findByUsuario(artefactoIn, usuario))                                //Si es valido, buscar el artefacto con el usuario
                .map(artefacto -> {
                    artefactoRepository.delete(artefacto);                                                              //Eliminar aretfacto
                    return ResponseEntity.ok(artefacto);                                                                //Enviar artefacto eliminado
                }).orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());                                      //Si no es valido, enviar error
    }

    /**
     * Descarga el artefacto especificado en {@param artefactoIn} de la evidencia especificada en {@param evidenciaIn}
     *
     * @param evidenciaIn El ID de una {@link com.pi.appraisal.entity.Evidencia}
     * @param artefactoIn El ID de un {@link com.pi.appraisal.entity.Artefacto}
     * @param credentials Las {@link Credentials} de la sesion
     * @return Los bytes del archivo si es aplicable
     */
    @GetMapping("{evidencia}/{artefacto}")
    public ResponseEntity<byte[]> getFile(@PathVariable("evidencia") Integer evidenciaIn,
                                          @PathVariable("artefacto") Integer artefactoIn,
                                          @RequestHeader("Credentials") Credentials credentials) {
        return session.authenticate(credentials, ORGANIZACION)                                                          //Valida las credenciales
                .map(usuario -> artefactoRepository.findByUsuario(evidenciaIn, artefactoIn, usuario))                   //Si es valido, buscar artefacto con el usuario y la evidencia
                .map(artefacto -> ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)                   //Especificar tipo de contenido
                        .body(artefacto.getArchivo())                                                                   //Enviar archivo
                ).orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());                                       //Si no es valido, enviar error
    }

    /**
     * Retorna la lista de {@link Artefacto} de la evidencia especificada en {@param evidenciaIn}
     *
     * @param evidenciaIn El ID de una {@link com.pi.appraisal.entity.Evidencia}
     * @param credentials Las {@link Credentials} de la sesion
     * @return La lista de {@link com.pi.appraisal.entity.Artefacto} si es aplicable
     */
    @GetMapping("{evidencia}")
    public ResponseEntity<List<Artefacto>> getAll(@PathVariable("evidencia") Integer evidenciaIn,
                                                  @RequestHeader("Credentials") Credentials credentials) {
        return session.authenticate(credentials, ORGANIZACION)                                                          //Valida las credenciales
                .map(usuario -> artefactoRepository.findAllByUsuario(evidenciaIn, usuario))                             //Si es valido, buscar artefactos con el usuario y la evidencia
                .map(ResponseEntity::ok)                                                                                //Enviar artefactos
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());                                        //Si no es valido, enviar error
    }
}
