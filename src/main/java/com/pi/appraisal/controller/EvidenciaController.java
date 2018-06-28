package com.pi.appraisal.controller;

import com.pi.appraisal.component.SessionCache;
import com.pi.appraisal.entity.Evidencia;
import com.pi.appraisal.repository.AreaProcesoRepository;
import com.pi.appraisal.repository.EvidenciaRepository;
import com.pi.appraisal.repository.InstanciaRepository;
import com.pi.appraisal.util.Credentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.pi.appraisal.entity.UsuarioRol.Priviledge.ORGANIZACION;

@RestController
@RequestMapping("api/evidencia")
public class EvidenciaController {

	private final InstanciaRepository instanciaRepository;
	private final EvidenciaRepository evidenciaRepository;
	private final AreaProcesoRepository areaProcesoRepository;
	private final SessionCache session;

	@Autowired
	public EvidenciaController(InstanciaRepository instanciaRepository, EvidenciaRepository evidenciaRepository, AreaProcesoRepository areaProcesoRepository, SessionCache session) {
		this.instanciaRepository = instanciaRepository;
		this.evidenciaRepository = evidenciaRepository;
		this.areaProcesoRepository = areaProcesoRepository;
		this.session = session;
	}

	@PostMapping("{instancia}/{area}")
	public ResponseEntity<List<Evidencia>> create(@PathVariable("instancia") Integer instanciaIn,
												  @PathVariable("area") Integer areaIn,
												  @RequestHeader("Credentials") Credentials credentials) {
		return session.authenticate(credentials, ORGANIZACION)
				.map(usuario -> instanciaRepository.findByUsuario(instanciaIn, usuario))
				.map(instancia -> areaProcesoRepository.findById(areaIn)
						.map(area -> {
							List<Evidencia> evidencias = new ArrayList<>();
							area.getMetaEspecificas().forEach(meta -> {
								meta.getPracticaEspecificas().forEach(practica -> {
									Evidencia evidencia = new Evidencia();
									evidencia.setInstancia(instancia);
									evidencia.setPracticaEspecifica(practica);
									evidencias.add(evidenciaRepository.save(evidencia));
								});
							});
							return ResponseEntity.ok(evidencias);
						}).orElse(ResponseEntity.notFound().build())
				).orElse(ResponseEntity.status(HttpStatus.FORBIDDEN).build());
	}

	@DeleteMapping("{instancia}")
	public ResponseEntity<String> delete(@PathVariable("instancia") Integer instanciaIn,
										 @RequestHeader("Credentials") Credentials credentials) {
		return session.authenticate(credentials, ORGANIZACION)
				.map(usuario -> instanciaRepository.findByUsuario(instanciaIn, usuario))
				.map(instancia -> {
					instancia.getEvidencias().forEach(evidenciaRepository::delete);
					return ResponseEntity.ok("Evidences deleted successfully");
				}).orElse(ResponseEntity.status(HttpStatus.FORBIDDEN).build());
	}

	@GetMapping("{instancia}/{area}")
	public ResponseEntity<List<Evidencia>> get(@PathVariable("instancia") Integer instanciaIn,
											   @PathVariable("area") Integer areaIn,
											   @RequestHeader("Credentials") Credentials credentials) {
		return session.authenticate(credentials, ORGANIZACION)
				.map(usuario -> instanciaRepository.findByUsuario(instanciaIn, usuario))
				.map(instancia -> areaProcesoRepository.findById(areaIn)
						.map(area -> ResponseEntity.ok(evidenciaRepository.findAllByArea(area, instancia)))
						.orElse(ResponseEntity.notFound().build())
				).orElse(ResponseEntity.status(HttpStatus.FORBIDDEN).build());
	}
}
