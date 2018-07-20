package com.pi.appraisal.controller;

import com.pi.appraisal.component.SessionCache;
import com.pi.appraisal.entity.Hipervinculo;
import com.pi.appraisal.entity.Hipervinculo.HipervinculoImpl;
import com.pi.appraisal.component.Impl;
import com.pi.appraisal.repository.EvidenciaRepository;
import com.pi.appraisal.repository.HipervinculoRepository;
import com.pi.appraisal.util.Credentials;
import com.pi.appraisal.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.pi.appraisal.entity.UsuarioRol.Priviledge.ORGANIZACION;

@RestController
@RequestMapping("api/hipervinculo")
public class HipervinculoController {

    private final EvidenciaRepository evidenciaRepository;
    private final HipervinculoRepository hipervinculoRepository;
    private final SessionCache session;

    @Autowired
    public HipervinculoController(
            EvidenciaRepository evidenciaRepository,
            HipervinculoRepository hipervinculoRepository,
            SessionCache session) {
        this.evidenciaRepository = evidenciaRepository;
        this.hipervinculoRepository = hipervinculoRepository;
        this.session = session;
    }

    /**
     * Crea un {@link com.pi.appraisal.entity.Hipervinculo} con el hipervinculo {@param hipervinculo} especificado
     * en {@param hipervinculo} en la evidencia especificada en {@param evidenciaIn}
     *
     * @param evidenciaIn  El ID de una {@link com.pi.appraisal.entity.Evidencia}
     * @param hipervinculo El ID de un {@link com.pi.appraisal.entity.Hipervinculo}
     * @param credentials  Las {@link Credentials} de la sesion
     * @return El {@link com.pi.appraisal.entity.Hipervinculo} creado si es aplicable
     */
    @PostMapping("{evidencia}")
    public ResponseEntity<HipervinculoImpl> add(@PathVariable("evidencia") Integer evidenciaIn,
                                                @RequestBody String hipervinculo,
                                                @RequestHeader("Credentials") String credentials) {
        return session.authenticate(credentials, ORGANIZACION)                                                          //Valida las credenciales
                .map(usuario -> evidenciaRepository.findByUsuario(evidenciaIn, usuario))                                //Si es valido, buscar evidencia con el usuario
                .map(evidencia -> {
                    Hipervinculo h = new Hipervinculo();                                                                //Crear hypervinculo
                    h.setLink(hipervinculo);
                    h.setEvidencia(evidencia);                                                                          //Asignar evidencia
                    return ResponseEntity.ok(Impl.to(hipervinculoRepository.save(h)));                                //Enviar hypervinculo
                }).orElse(ResponseEntity.status(HttpStatus.FORBIDDEN).build());                                         //Si no es valido, enviar error
    }

    /**
     * Elimina el hipervinculo especificado en {@param hipervinculoIn}
     *
     * @param hipervinculoIn El ID de un {@link com.pi.appraisal.entity.Hipervinculo}
     * @param credentials    Las {@link Credentials} de la sesion
     * @return El mensaje de elminado si es aplicable
     */
    @DeleteMapping("{hipervinculo}")
    public ResponseEntity<Response> delete(@PathVariable("hipervinculo") Integer hipervinculoIn,
                                           @RequestHeader("Credentials") String credentials) {
        return session.authenticate(credentials, ORGANIZACION)                                                          //Valida las credenciales
                .map(usuario -> hipervinculoRepository.findByUsuario(hipervinculoIn, usuario))                          //Si es valido, buscar hypervinculo con el usuario
                .map(hipervinculo -> {
                    hipervinculoRepository.delete(hipervinculo);                                                        //Eliminar hypervinculo
                    return Response.ok("Deleted Successfully");                                                   //Enviar respuesta
                }).orElse(ResponseEntity.status(HttpStatus.FORBIDDEN).build());                                         //Si no es valido, enviar error
    }

    /**
     * Retorna la lista de {@link com.pi.appraisal.entity.Hipervinculo} de la instancia especificado
     * en {@param instanciaIn}
     *
     * @param evidenciaIn El ID de un {@link com.pi.appraisal.entity.Evidencia}
     * @param credentials Las {@link Credentials} de la sesion
     * @return La lista de {@link com.pi.appraisal.entity.Hipervinculo} si es aplicable
     */
    @GetMapping("{evidencia}")
    public ResponseEntity<List<HipervinculoImpl>> getAll(@PathVariable("evidencia") Integer evidenciaIn,
                                                         @RequestHeader("Credentials") String credentials) {
        return session.authenticate(credentials, ORGANIZACION)                                                          //Valida las credenciales
                .map(usuario -> hipervinculoRepository.findAllByUsuario(evidenciaIn, usuario).stream()                  //Si es valido, buscar hypervinculos con el usuario y la evidencia
                        .map(Impl::to)
                        .collect(Collectors.toList())
                ).map(ResponseEntity::ok)                                                                               //Enviar hypervinculos
                .orElse(ResponseEntity.status(HttpStatus.FORBIDDEN).build());                                           //Si no es valido, enviar error
    }

}
