package com.pi.appraisal.controller;

import com.pi.appraisal.component.Impl;
import com.pi.appraisal.component.SessionCache;
import com.pi.appraisal.entity.Organizacion.OrganizacionImpl;
import com.pi.appraisal.repository.OrganizacionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.pi.appraisal.entity.UsuarioRol.Priviledge.ADMINISTRADOR;

@RestController
@RequestMapping("api/organizacion")
public class OrganizacionController {

    private final OrganizacionRepository organizacionRepository;
    private final SessionCache session;

    public OrganizacionController(OrganizacionRepository organizacionRepository, SessionCache session) {
        this.organizacionRepository = organizacionRepository;
        this.session = session;
    }

    @GetMapping("{id}")
    public ResponseEntity<OrganizacionImpl> get(@PathVariable("id") Integer id,
                                                @RequestHeader("Credentials") String credentials) {
        return session.authenticate(credentials, ADMINISTRADOR)
                .pipe(() -> organizacionRepository.findById(id)
                        .map(Impl::to)
                        .map(ResponseEntity::ok)
                        .orElse(ResponseEntity.notFound().build())
                ).orElse(ResponseEntity.status(HttpStatus.FORBIDDEN).build());
    }
}
