package com.pi.appraisal.controller;

import com.pi.appraisal.component.SessionCache;
import com.pi.appraisal.entity.Artefacto;
import com.pi.appraisal.entity.Evidencia;
import com.pi.appraisal.entity.Hipervinculo;
import com.pi.appraisal.entity.Instancia;
import com.pi.appraisal.repository.OrganizacionRepository;
import com.pi.appraisal.util.Credentials;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
		return session.authenticate(credentials, ADMINISTRADOR)
				.map(usuario -> organizacionRepository.findByIdAndUsuario(organizacionIn, usuario))
				.map(organizacion -> {
					Status status = null;
					outerLoop: for (Instancia instancia : organizacion.getInstancias()) {
						innerLoop: for (Evidencia evidencia : instancia.getEvidencias()) {
							Set<Artefacto> arts = evidencia.getArtefactos();
							Set<Hipervinculo> hips = evidencia.getHipervinculos();
							if (status == null) {
								if (arts.isEmpty() && hips.isEmpty()) status = Status.NO_CUMPLE;
								else status = Status.SI_CUMPLE;
							} else switch (status) {
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
					return ResponseEntity.ok(status != null ? status : Status.NO_CUMPLE);
				}).orElse(ResponseEntity.status(HttpStatus.FORBIDDEN).build());
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
