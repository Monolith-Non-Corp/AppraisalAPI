package com.pi.appraisal.controller;

import com.pi.appraisal.component.Impl;
import com.pi.appraisal.component.SessionCache;
import com.pi.appraisal.entity.Evidencia;
import com.pi.appraisal.entity.Evidencia.EvidenciaImpl;
import com.pi.appraisal.repository.AreaProcesoRepository;
import com.pi.appraisal.repository.EvidenciaRepository;
import com.pi.appraisal.repository.InstanciaRepository;
import com.pi.appraisal.util.Credentials;
import com.pi.appraisal.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.pi.appraisal.entity.UsuarioRol.Priviledge.ORGANIZACION;

@RestController
@RequestMapping("api/evidencia")
public class EvidenciaController {

    private final InstanciaRepository instanciaRepository;
    private final EvidenciaRepository evidenciaRepository;
    private final AreaProcesoRepository areaProcesoRepository;
    private final SessionCache session;

    @Autowired
    public EvidenciaController(
            InstanciaRepository instanciaRepository,
            EvidenciaRepository evidenciaRepository,
            AreaProcesoRepository areaProcesoRepository,
            SessionCache session) {
        this.instanciaRepository = instanciaRepository;
        this.evidenciaRepository = evidenciaRepository;
        this.areaProcesoRepository = areaProcesoRepository;
        this.session = session;
    }

    /**
     * Crea todas las {@link com.pi.appraisal.entity.Evidencia} de una instancia especificada
     * en {@param instanciaIn} en el area especificada en {@param areaIn}
     *
     * @param instanciaIn El ID de una {@link com.pi.appraisal.entity.Instancia}
     * @param areaIn      El ID de una {@link com.pi.appraisal.entity.AreaProceso}
     * @param credentials Las {@link Credentials} de la sesion
     * @return La lista de {@link com.pi.appraisal.entity.Evidencia} creadas si es aplicable
     */
    @PostMapping("{instancia}/{area}")
    public ResponseEntity<List<EvidenciaImpl>> create(@PathVariable("instancia") Integer instanciaIn,
                                                      @PathVariable("area") Integer areaIn,
                                                      @RequestHeader("Credentials") String credentials) {
        return session.authenticate(credentials, ORGANIZACION)                                                          //Valida las credenciales
                .map(usuario -> instanciaRepository.findByUsuario(instanciaIn, usuario))                                //Si es valido, buscar la instancia con el usuario
                .map(instancia -> areaProcesoRepository.findById(areaIn)                                                //Si existe, buscar la area especificada
                        .map(area -> {
                            List<EvidenciaImpl> evidencias = new ArrayList<>();                                         //Crear lista de evidencias
                            area.getMetaEspecificas().forEach(meta -> {                                                 //Buscar metas de la area especificada
                                meta.getPracticaEspecificas().forEach(practica -> {                                     //Buscar practicas de la meta
                                    Evidencia evidencia = new Evidencia();                                              //Crear evidencia
                                    evidencia.setInstancia(instancia);                                                  //Asignar instancia
                                    evidencia.setPracticaEspecifica(practica);                                          //Asignar practica
                                    evidencias.add(Impl.from(evidenciaRepository.save(evidencia)));                     //Añadir evidencia a la lista
                                });
                            });
                            return ResponseEntity.ok(evidencias);                                                       //Enviar evidencias
                        }).orElse(ResponseEntity.notFound().build())                                                    //Si no existe, enviar error
                ).orElse(ResponseEntity.status(HttpStatus.FORBIDDEN).build());                                          //Si no es valido, enviar error
    }

    /**
     * Elimina todas las {@link com.pi.appraisal.entity.Evidencia} de la instancia especificada en {@param instanciaIn}
     *
     * @param instanciaIn El ID de una {@link com.pi.appraisal.entity.Instancia}
     * @param credentials Las {@link Credentials} de la sesion
     * @return El mensaje de elminado si es aplicable
     */
    @DeleteMapping("{instancia}")
    public ResponseEntity<Response> delete(@PathVariable("instancia") Integer instanciaIn,
                                           @RequestHeader("Credentials") String credentials) {
        return session.authenticate(credentials, ORGANIZACION)                                                          //Valida las credenciales
                .map(usuario -> instanciaRepository.findByUsuario(instanciaIn, usuario))                                //Si es valido, buscar instancia con el usuario
                .map(instancia -> {
                    instancia.getEvidencias().forEach(evidenciaRepository::delete);                                     //Eliminar cada evidencia de la instancia
                    return Response.ok("Evidences deleted successfully");                                         //Enviar mensaje
                }).orElse(ResponseEntity.status(HttpStatus.FORBIDDEN).build());                                         //Si no es valido, enviar error
    }

    /**
     * Retorna la lista de {@link com.pi.appraisal.entity.Evidencia} de la instancia especificada
     * en {@param instanciaIn} en su area especificada en {@param areaIn}
     *
     * @param instanciaIn El ID de una {@link com.pi.appraisal.entity.Instancia}
     * @param areaIn      El ID de una {@link com.pi.appraisal.entity.AreaProceso}
     * @param credentials Las {@link Credentials} de la sesion
     * @return La lista de {@link com.pi.appraisal.entity.Evidencia} si es aplicable
     */
    @GetMapping("{instancia}/{area}")
    public ResponseEntity<List<EvidenciaImpl>> get(@PathVariable("instancia") Integer instanciaIn,
                                                   @PathVariable("area") Integer areaIn,
                                                   @RequestHeader("Credentials") String credentials) {
        return session.authenticate(credentials, ORGANIZACION)                                                          //Valida las credenciales
                .map(usuario -> instanciaRepository.findByUsuario(instanciaIn, usuario))                                //Si es valido, buscar instancia con el usuario
                .map(instancia -> areaProcesoRepository.findById(areaIn)                                                //Si existe, buscar la area especificada
                        .map(area -> evidenciaRepository.findAllByArea(area, instancia).stream()                        //Buscar evidencias por area e instancia
                                .map(Impl::from)
                                .collect(Collectors.toList())
                        ).map(ResponseEntity::ok)                                                                       //Enviar evidencias
                        .orElse(ResponseEntity.notFound().build())                                                      //Si no existe, enviar error
                ).orElse(ResponseEntity.status(HttpStatus.FORBIDDEN).build());                                          //Si no es valido, enviar error
    }
}
