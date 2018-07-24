package com.pi.appraisal.controller;

import com.pi.appraisal.component.Impl;
import com.pi.appraisal.component.SessionCache;
import com.pi.appraisal.entity.Artefacto;
import com.pi.appraisal.entity.Evidencia;
import com.pi.appraisal.entity.Hipervinculo;
import com.pi.appraisal.entity.Instancia;
import com.pi.appraisal.entity.PracticaEspecifica.PracticaEspecificaImpl;
import com.pi.appraisal.repository.OrganizacionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.pi.appraisal.entity.UsuarioRol.Priviledge.ADMINISTRADOR;

@RestController
@RequestMapping("api/evaluacion")
public class EvaluacionController {

    private final OrganizacionRepository organizacionRepository;
    private final SessionCache session;

    public EvaluacionController(OrganizacionRepository organizacionRepository, SessionCache session) {
        this.organizacionRepository = organizacionRepository;
        this.session = session;
    }

    @GetMapping("{organizacion}")
    public ResponseEntity<StatusImpl> validate(@PathVariable("organizacion") Integer organizacionIn,
                                           @RequestHeader("Credentials") String credentials) {
        return session.authenticate(credentials, ADMINISTRADOR)                                                         //Valida las credenciales
                .map(usuario -> organizacionRepository.findById(organizacionIn).map(organizacion -> {
                    Status status = null;
                    outerLoop: for (Instancia instancia : organizacion.getInstancias()) {
                        Set<Evidencia> evidencias = instancia.getEvidencias();
                        innerLoop: for (Evidencia evidencia : evidencias) {
                            Set<Artefacto> arts = evidencia.getArtefactos();
                            Set<Hipervinculo> hips = evidencia.getHipervinculos();
                            if (status == null) {
                                if (arts.isEmpty() && hips.isEmpty()) status = Status.NO_CUMPLE;
                                else status = Status.SI_CUMPLE;
                            } else{
                                switch (status) {
                                    case NO_CUMPLE:
                                        if (!arts.isEmpty() || !hips.isEmpty()) {
                                            status = Status.EN_PROGRESO;
                                            break outerLoop;
                                        } else {
                                            continue innerLoop;
                                        }
                                    case SI_CUMPLE:
                                        if (arts.isEmpty() && hips.isEmpty()) {
                                            status = Status.EN_PROGRESO;
                                            break outerLoop;
                                        } else {
                                           continue innerLoop;
                                        }
                                    default: 
                                }
                            }
                        }
                    }
                    return ResponseEntity.ok(status != null ? status.send() : Status.NO_CUMPLE.send());                               //Si todas tienen evidencias, cumple, si una no tiene, en progreso, si ninguna cunple,
                }).orElse(ResponseEntity.notFound().build()))
                .orElse(ResponseEntity.status(HttpStatus.FORBIDDEN).build());                                           //Si no es valido, enviar error
    }

    @GetMapping("missing/{organizacion}")
    public ResponseEntity<List<PracticaEspecificaImpl>> getPracticas(@PathVariable("organizacion") Integer organizacionIn,
                                                                     @RequestHeader("Credentials") String credentials) {
        return session.authenticate(credentials, ADMINISTRADOR)                                                         //Valida las credenciales
                .map(usuario -> organizacionRepository.findById(organizacionIn).map(organizacion -> {
                    List<PracticaEspecificaImpl> list = new ArrayList<>();                                              //Crear lista de evidencias incompletas
                    organizacion.getInstancias().forEach(instancia -> instancia.getEvidencias().forEach(evidencia -> {  //Por cada instancia
                        if (evidencia.getArtefactos().isEmpty() && evidencia.getHipervinculos().isEmpty()) {            //Si no tiene evidencias
                            list.add(Impl.to(evidencia.getPracticaEspecifica()));
                        }
                    }));
                    return ResponseEntity.ok(list);                                                                     //Regresar lista
                }).orElse(ResponseEntity.notFound().build()))
                .orElse(ResponseEntity.status(HttpStatus.FORBIDDEN).build());                                           //Si no es valido, enviar error
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

        public StatusImpl send() {
            StatusImpl status = new StatusImpl();
            status.message =  message;
            status.color = color;
            return status;
        }
    }

    public static class StatusImpl {
        public String message;
        public Integer color;
    }
}
