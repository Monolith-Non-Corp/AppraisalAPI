package com.pi.appraisal.controller;

import com.pi.appraisal.component.SessionCache;
import com.pi.appraisal.entity.Hipervinculo;
import com.pi.appraisal.repository.EvidenciaRepository;
import com.pi.appraisal.repository.HipervinculoRepository;
import com.pi.appraisal.util.Credentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.pi.appraisal.entity.UsuarioRol.Priviledge.ORGANIZACION;

@RestController
@RequestMapping("api/hipervinculo")
public class HipervinculoController {

	private final EvidenciaRepository evidenciaRepository;
	private final HipervinculoRepository hipervinculoRepository;
	private final SessionCache session;

	@Autowired
	public HipervinculoController(EvidenciaRepository evidenciaRepository, HipervinculoRepository hipervinculoRepository, SessionCache session) {
		this.evidenciaRepository = evidenciaRepository;
		this.hipervinculoRepository = hipervinculoRepository;
		this.session = session;
	}

	@PostMapping("{evidencia}")
	public ResponseEntity<Hipervinculo> add(@PathVariable("evidencia") Integer evidenciaIn,
											@RequestBody Hipervinculo hipervinculo,
											@RequestHeader("Credentials") Credentials credentials) {
		return session.authenticate(credentials, ORGANIZACION)
				.map(usuario -> evidenciaRepository.findByUsuario(evidenciaIn, usuario))
				.map(evidencia -> {
					Hipervinculo h = new Hipervinculo();
					h.setLink(hipervinculo.getLink());
					h.setEvidencia(evidencia);
					return ResponseEntity.ok(hipervinculoRepository.save(h));
				}).orElse(ResponseEntity.status(HttpStatus.FORBIDDEN).build());
	}

	@DeleteMapping("{hipervinculo}")
	public ResponseEntity<String> remove(@PathVariable("hipervinculo") Integer hipervinculoIn,
										 @RequestHeader("Credentials") Credentials credentials) {
		return session.authenticate(credentials, ORGANIZACION)
				.map(usuario -> hipervinculoRepository.findByUsuario(hipervinculoIn, usuario))
				.map(hipervinculo -> {
					hipervinculoRepository.delete(hipervinculo);
					return ResponseEntity.ok("Hyperlink deleted successfully");
				}).orElse(ResponseEntity.status(HttpStatus.FORBIDDEN).build());
	}

	@GetMapping("{hipervinculo}")
	public ResponseEntity<List<Hipervinculo>> getAll(@PathVariable("hypervinculo") Integer hipervinculoIn,
													 @RequestHeader("Credentials") Credentials credentials) {
		return session.authenticate(credentials, ORGANIZACION)
				.map(usuario -> hipervinculoRepository.findAllByUsuario(hipervinculoIn, usuario))
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.status(HttpStatus.FORBIDDEN).build());
	}

}
