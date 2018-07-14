package com.pi.appraisal.controller;

import com.pi.appraisal.component.Impl;
import com.pi.appraisal.component.SessionCache;
import com.pi.appraisal.entity.Evidencia;
import com.pi.appraisal.entity.Instancia;
import com.pi.appraisal.entity.Instancia.InstanciaImpl;
import com.pi.appraisal.entity.InstanciaTipo.InstanciaTipoImpl;
import com.pi.appraisal.repository.*;
import com.pi.appraisal.util.Credentials;
import com.pi.appraisal.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.pi.appraisal.entity.UsuarioRol.Priviledge.ADMINISTRADOR;
import static com.pi.appraisal.entity.UsuarioRol.Priviledge.ANY;

@RestController
@RequestMapping("api/instancia")
public class InstanciaController {

    private final InstanciaRepository instanciaRepository;
    private final EvidenciaRepository evidenciaRepository;
    private final AreaProcesoRepository areaProcesoRepository;
    private final InstanciaTipoRepository instanciaTipoRepository;
    private final OrganizacionRepository organizacionRepository;
    private final SessionCache session;

    @Autowired
    public InstanciaController(
            InstanciaRepository instanciaRepository,
            EvidenciaRepository evidenciaRepository,
            AreaProcesoRepository areaProcesoRepository,
            InstanciaTipoRepository instanciaTipoRepository, OrganizacionRepository organizacionRepository, SessionCache session) {
        this.instanciaRepository = instanciaRepository;
        this.evidenciaRepository = evidenciaRepository;
        this.areaProcesoRepository = areaProcesoRepository;
        this.instanciaTipoRepository = instanciaTipoRepository;
        this.organizacionRepository = organizacionRepository;
        this.session = session;
    }

    @GetMapping("tipos")
    public ResponseEntity<List<InstanciaTipoImpl>> getTipos() {
        return ResponseEntity.ok(instanciaTipoRepository.findAll().stream().map(Impl::to).collect(Collectors.toList()));
    }

    @GetMapping("{organizacion}")
    public ResponseEntity<List<InstanciaImpl>> getAll(@PathVariable("organizacion") Integer organizacionIn,
                                                      @RequestHeader("Credentials") String credentials) {
        return session.authenticate(credentials, ANY).pipe(() -> organizacionRepository.findById(organizacionIn)
                .map(organizacion ->
                        ResponseEntity.ok(instanciaRepository.findAllByOrganizacion(organizacion).stream()
                                .map(Impl::to)
                                .collect(Collectors.toList()))
                ).orElse(ResponseEntity.notFound().build())
        ).orElse(ResponseEntity.status(HttpStatus.FORBIDDEN).build());
    }

    /**
     * Crea todas las {@link com.pi.appraisal.entity.Evidencia} de una instancia especificada
     * en {@param instanciaIn} en el area especificada en {@param areaIn}
     *
     * @param instanciaIn La {@link com.pi.appraisal.entity.Instancia}
     * @param credentials Las {@link Credentials} de la sesion
     * @return La lista de {@link com.pi.appraisal.entity.Evidencia} creadas si es aplicable
     */
    @PostMapping("{organizacion}")
    public ResponseEntity<InstanciaImpl> create(@RequestBody InstanciaImpl instanciaIn,
                                                @PathVariable("organizacion") Integer organizacionIn,
                                                @RequestHeader("Credentials") String credentials) {
        return session.authenticate(credentials, ADMINISTRADOR)                                                         //Valida las credenciales
                .pipe(() ->
                    organizacionRepository.findById(organizacionIn).map(organizacion ->                                 //Si existe, buscar la organizacion
                        instanciaTipoRepository.findById(instanciaIn.instanciaTipo.id).map(instanciaTipo -> {           //Si existe, buscar la instanciaTipo
                            Instancia instancia = Impl.from(instanciaIn);                                               //Crear instancia
                            instancia.setOrganizacion(organizacion);                                                    //Asignar organizacion
                            instancia.setInstanciaTipo(instanciaTipo);                                                  //Asignar instanciaTipo
                            Instancia ins = instanciaRepository.save(instancia);                                        //Registrar
                            instanciaIn.areaProcesos.forEach(areaIn -> {
                                areaProcesoRepository.findById(areaIn.id).ifPresent(areaProceso -> {                    //Si existe, buscar la areaProceso
                                    areaProceso.getMetaEspecificas().forEach(metaEspecifica -> {                        //Buscar metas de la area especificada
                                        metaEspecifica.getPracticaEspecificas().forEach(practicaEspecifica -> {         //Buscar practicas de la meta
                                            Evidencia evidencia = new Evidencia();                                      //Crear evidencia
                                            evidencia.setInstancia(ins);                                                //Asignar instancia
                                            evidencia.setPracticaEspecifica(practicaEspecifica);                        //Asignar practica
                                            evidenciaRepository.save(evidencia);                                        //Registrar
                                        });
                                    });
                                });
                            });
                            return instanciaRepository.findById(ins.getId()).map(Impl::to).map(ResponseEntity::ok)
                                    .orElse(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());           //Enviar instancia
                        }).orElse(ResponseEntity.notFound().build())                                                    //Si no existe, enviar error
                    ).orElse(ResponseEntity.notFound().build())                                                         //Si no existe, enviar error
                ).orElse(ResponseEntity.status(HttpStatus.FORBIDDEN).build());                                          //Si no es valido, enviar error
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response> delete(@PathVariable("id") Integer id,
                                           @RequestHeader("Credentials") String credentials) {
        return session.authenticate(credentials, ADMINISTRADOR)
                .pipe(() -> instanciaRepository.findById(id).map(instancia -> {
                            instanciaRepository.delete(instancia);
                            return Response.ok("Deleted Successfully");
                }).orElse(ResponseEntity.notFound().build()))
                .orElse(ResponseEntity.status(HttpStatus.FORBIDDEN).build());
    }
}
