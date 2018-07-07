package com.pi.appraisal.controller;

import com.pi.appraisal.component.Impl;
import com.pi.appraisal.component.SessionCache;
import com.pi.appraisal.entity.*;
import com.pi.appraisal.entity.PracticaEspecifica.PracticaEspecificaImpl;
import com.pi.appraisal.repository.OrganizacionRepository;
import com.pi.appraisal.util.Credentials;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.pi.appraisal.entity.UsuarioRol.Priviledge.ADMINISTRADOR;

@RestController
@RequestMapping("api/evaluacion")
public class EvaluacionRepository {

    private final OrganizacionRepository organizacionRepository;
    private final SessionCache session;

    public EvaluacionRepository(OrganizacionRepository organizacionRepository, SessionCache session) {
        this.organizacionRepository = organizacionRepository;
        this.session = session;
    }

    @GetMapping("{organizacion}")
    public ResponseEntity<Status> validate(@PathVariable("organizacion") Integer organizacionIn,
                                           @RequestHeader("Credentials") Credentials credentials) {
        return session.authenticate(credentials, ADMINISTRADOR)                                                         //Valida las credenciales
                .map(usuario -> organizacionRepository.findByIdAndUsuario(organizacionIn, usuario))                     //Si es valido, busca la organizacion
                .map(organizacion -> {
                    Status status = null;
                    outerLoop:
                    for (Instancia instancia : organizacion.getInstancias()) {                                          //Por cada instancia de la organizacion
                        innerLoop:
                        for (Evidencia evidencia : instancia.getEvidencias()) {                                         //Por cada evidencia de la instancia
                            Set<Artefacto> arts = evidencia.getArtefactos();
                            Set<Hipervinculo> hips = evidencia.getHipervinculos();
                            if (status == null) {
                                if (arts.isEmpty() && hips.isEmpty()) status = Status.NO_CUMPLE;                        //Si ninguna cumple, no cumple
                                else status = Status.SI_CUMPLE;                                                         //Si todas tienen evidencias, cumple
                            } else switch (status) {                                                                    //Si una no tiene, en progreso
                                case NO_CUMPLE:
                                    if (!arts.isEmpty() || !hips.isEmpty()) {
                                        status = Status.EN_PROGRESO;
                                        break outerLoop;
                                    }
                                    break innerLoop;
                                case SI_CUMPLE:
                                    if (arts.isEmpty() && hips.isEmpty()) {
                                        status = Status.EN_PROGRESO;
                                        break outerLoop;
                                    }
                                    break innerLoop;
                            }
                        }
                    }
                    return ResponseEntity.ok(status != null ? status : Status.NO_CUMPLE);                               //Si todas tienen evidencias, cumple, si una no tiene, en progreso, si ninguna cunple,
                }).orElse(ResponseEntity.status(HttpStatus.FORBIDDEN).build());                                         //Si no es valido, enviar error
    }

    @GetMapping("missing/{organizacion}")
    public ResponseEntity<List<PracticaEspecificaImpl>> getPracticas(@PathVariable("organizacion") Integer organizacionIn,
                                                                     @RequestHeader("Credentials") Credentials credentials) {
        return session.authenticate(credentials, ADMINISTRADOR)                                                         //Valida las credenciales
                .map(usuario -> organizacionRepository.findByIdAndUsuario(organizacionIn, usuario))                     //Si es valido, busca la organizacion
                .map(organizacion -> {
                    List<PracticaEspecificaImpl> list = new ArrayList<>();                                              //Crear lista de evidencias incompletas
                    organizacion.getInstancias().forEach(instancia -> instancia.getEvidencias().forEach(evidencia -> {  //Por cada instancia
                        if (evidencia.getArtefactos().isEmpty() && evidencia.getHipervinculos().isEmpty()) {            //Si no tiene evidencias
                            list.add(Impl.from(evidencia.getPracticaEspecifica()));
                        }
                    }));
                    return ResponseEntity.ok(list);                                                                     //Regresar lista
                }).orElse(ResponseEntity.status(HttpStatus.FORBIDDEN).build());                                         //Si no es valido, enviar error
    }

    public enum Status {
        NO_CUMPLE("No cumple", 0xFF0000),
        EN_PROGRESO("En progreso", 0xFFFF00),
        SI_CUMPLE("Si cumple", 0x00FF00);

        public String message;
        public int color;

        Status(String message, int color) {
            this.message = message;
            this.color = color;
        }
    }
}
