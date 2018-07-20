package com.pi.appraisal.controller;

import com.pi.appraisal.component.Impl;
import com.pi.appraisal.component.SessionCache;
import com.pi.appraisal.entity.Evidencia.EvidenciaImpl;
import com.pi.appraisal.repository.EvidenciaRepository;
import com.pi.appraisal.repository.InstanciaRepository;
import com.pi.appraisal.repository.PracticaEspecificaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.pi.appraisal.entity.UsuarioRol.Priviledge.ORGANIZACION;

@RestController
@RequestMapping("api/evidencia")
public class EvidenciaController {

    private final InstanciaRepository instanciaRepository;
    private final EvidenciaRepository evidenciaRepository;
    private final PracticaEspecificaRepository practicaEspecificaRepository;
    private final SessionCache session;

    @Autowired
    public EvidenciaController(
            InstanciaRepository instanciaRepository,
            EvidenciaRepository evidenciaRepository,
            PracticaEspecificaRepository practicaEspecificaRepository, SessionCache session) {
        this.instanciaRepository = instanciaRepository;
        this.evidenciaRepository = evidenciaRepository;
        this.practicaEspecificaRepository = practicaEspecificaRepository;
        this.session = session;
    }

    @GetMapping("{instancia}/{practica}")
    public ResponseEntity<EvidenciaImpl> get(@PathVariable("instancia") Integer instanciaIn,
                                             @PathVariable("practica") Integer practicaIn,
                                             @RequestHeader("Credentials") String credentials) {
        return session.authenticate(credentials, ORGANIZACION)
                .map(usuario -> instanciaRepository.findByUsuario(instanciaIn, usuario))
                .map(instancia -> practicaEspecificaRepository.findById(practicaIn)
                        .map(area -> evidenciaRepository.findByInstanciaAndPracticaEspecifica(instancia, area))
                        .map(Impl::to).map(ResponseEntity::ok)
                        .orElse(ResponseEntity.notFound().build())
                ).orElse(ResponseEntity.status(HttpStatus.FORBIDDEN).build());
    }
}
