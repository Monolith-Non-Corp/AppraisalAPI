package com.pi.appraisal.controller;

import com.pi.appraisal.component.Impl;
import com.pi.appraisal.component.SessionCache;
import com.pi.appraisal.entity.Usuario;
import com.pi.appraisal.entity.Usuario.UsuarioImpl;
import com.pi.appraisal.repository.EvidenciaRepository;
import com.pi.appraisal.repository.NivelRepository;
import com.pi.appraisal.repository.UsuarioRepository;
import com.pi.appraisal.repository.UsuarioRolRepository;
import com.pi.appraisal.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.pi.appraisal.entity.UsuarioRol.Priviledge.ADMINISTRADOR;

@RestController
@RequestMapping("api/user")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioRolRepository usuarioRolRepository;
    private final NivelRepository nivelRepository;
    private final EvidenciaRepository evidenciaRepository;
    private final SessionCache session;

    @Autowired
    public UsuarioController(
            UsuarioRepository usuarioRepository,
            UsuarioRolRepository usuarioRolRepository,
            NivelRepository nivelRepository,
            EvidenciaRepository evidenciaRepository,
            SessionCache session) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioRolRepository = usuarioRolRepository;
        this.nivelRepository = nivelRepository;
        this.evidenciaRepository = evidenciaRepository;
        this.session = session;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioImpl>> getAll(@RequestHeader("Credentials") String credentials) {
        return session.authenticate(credentials, ADMINISTRADOR)
                .pipe(() -> ResponseEntity.ok(usuarioRepository
                        .findAllByUsuarioRolIsNotLike(usuarioRolRepository.getAdministrador())
                        .stream().map(Impl::to).collect(Collectors.toList()))
                ).orElse(ResponseEntity.status(HttpStatus.FORBIDDEN).build());
    }

    @GetMapping("username")
    public ResponseEntity<Response> getIsEmailTaken(@RequestParam("email") String email,
                                                    @RequestHeader("Credentials") String credentials) {
        return session.authenticate(credentials, ADMINISTRADOR).pipe(() -> {
            boolean valid = !usuarioRepository.findByUsername(email).isPresent();
            return Response.ok(valid ? EmailStatus.AVAILABLE.name() : EmailStatus.UN_AVAILABLE.name());
        }).orElse(ResponseEntity.status(HttpStatus.FORBIDDEN).build());
    }

    @PostMapping
    public ResponseEntity<UsuarioImpl> create(@RequestBody UsuarioImpl usuarioIn,
                                              @RequestHeader("Credentials") String credentials) {
        return session.authenticate(credentials, ADMINISTRADOR)
                .pipe(() -> {
                    Usuario usuario = Impl.from(usuarioIn);
                    usuario.setUsuarioRol(usuarioRolRepository.getOrganizacion());
                    nivelRepository.findByLvl(usuarioIn.organizacion.nivel.lvl).ifPresent(nivel -> {
                        usuario.getOrganizacion().setNivel(nivel);
                    });
                    return ResponseEntity.ok(Impl.to(usuarioRepository.save(usuario)));
                }).orElse(ResponseEntity.status(HttpStatus.FORBIDDEN).build());
    }

    @PutMapping("{id}")
    public ResponseEntity<UsuarioImpl> update(@PathVariable("id") Integer id,
                                              @RequestBody UsuarioImpl usuarioIn,
                                              @RequestHeader("Credentials") String credentials) {
        return session.authenticate(credentials, ADMINISTRADOR)
                .pipe(() -> usuarioRepository.findById(id).map(usuario -> {
                    usuario.setUsername(usuarioIn.username);
                    usuario.setPassword(usuarioIn.password);
                    usuario.getPersona().setNombre(usuarioIn.persona.nombre);
                    usuario.getPersona().setPrimerApellido(usuarioIn.persona.primerApellido);
                    usuario.getPersona().setSegundoApellido(usuarioIn.persona.segundoApellido);
                    usuario.getOrganizacion().setNombre(usuarioIn.organizacion.nombre);
                    int lastLvl = usuario.getOrganizacion().getNivel().getLvl();
                    int newLvl = usuarioIn.organizacion.nivel.lvl;
                    for (int lvl = lastLvl; lvl > newLvl; lvl--) {
                        nivelRepository.findByLvl(lvl).ifPresent(nivel -> {
                            nivel.getAreaProcesos().forEach(areaProceso -> {
                                usuario.getOrganizacion().getInstancias().forEach(instancia -> {
                                    evidenciaRepository.deleteInBatch(evidenciaRepository.findAllByArea(areaProceso.getId(), instancia));
                                });
                            });
                        });
                    }
                    nivelRepository.findByLvl(usuarioIn.organizacion.nivel.lvl).ifPresent(nivel -> {
                        usuario.getOrganizacion().setNivel(nivel);
                    });
                    return ResponseEntity.ok(Impl.to(usuarioRepository.save(usuario)));
                }).orElse(ResponseEntity.notFound().build()))
                .orElse(ResponseEntity.status(HttpStatus.FORBIDDEN).build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response> delete(@PathVariable("id") Integer id,
                                           @RequestHeader("Credentials") String credentials) {
        return session.authenticate(credentials, ADMINISTRADOR)
                .pipe(() -> usuarioRepository.findById(id).map(usuario -> {
                    usuarioRepository.delete(usuario);
                    session.remove(id);
                    return Response.ok("Deleted successfully");
                }).orElse(ResponseEntity.notFound().build()))
                .orElse(ResponseEntity.status(HttpStatus.FORBIDDEN).build());
    }

    enum EmailStatus {
        UN_AVAILABLE,
        AVAILABLE
    }
}
