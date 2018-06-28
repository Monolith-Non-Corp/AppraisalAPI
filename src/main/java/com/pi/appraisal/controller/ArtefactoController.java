package com.pi.appraisal.controller;

import com.pi.appraisal.component.SessionCache;
import com.pi.appraisal.entity.Artefacto;
import com.pi.appraisal.repository.ArtefactoRepository;
import com.pi.appraisal.repository.EvidenciaRepository;
import com.pi.appraisal.util.Credentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.pi.appraisal.entity.UsuarioRol.Priviledge.ORGANIZACION;

@RestController
@RequestMapping("api/file")
public class ArtefactoController {

	private final ArtefactoRepository artefactoRepository;
	private final EvidenciaRepository evidenciaRepository;
	private final SessionCache session;

	@Autowired
	public ArtefactoController(ArtefactoRepository artefactoRepository, EvidenciaRepository evidenciaRepository, SessionCache session) {
		this.artefactoRepository = artefactoRepository;
		this.evidenciaRepository = evidenciaRepository;
		this.session = session;
	}

	@PostMapping("{evidencia}")
	public ResponseEntity<Artefacto> upload(@PathVariable("evidencia") Integer evidenciaIn,
											@RequestParam("file") MultipartFile file,
											@RequestHeader("Credentials") Credentials credentials) {
		return session.authenticate(credentials, ORGANIZACION)
				.map(usuario -> evidenciaRepository.findByUsuario(evidenciaIn, usuario)).map(evidencia -> {
					if (file.isEmpty()) {
						return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).<Artefacto>build();
					}
					byte[] bytes;
					try {
						bytes = file.getBytes();
					} catch (IOException e) {
						e.printStackTrace();
						return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).<Artefacto>build();
					}
					Artefacto artefacto = new Artefacto();
					artefacto.setArchivo(bytes);
					artefacto.setNombre(file.getOriginalFilename());
					artefacto.setTipo(file.getContentType());
					artefacto.setEvidencia(evidencia);
					return ResponseEntity.ok(artefactoRepository.save(artefacto));
				}).orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}

	@DeleteMapping("{artefacto}")
	public ResponseEntity<String> delete(@PathVariable("artefacto") Integer artefactoIn,
										 @RequestHeader("Credentials") Credentials credentials) {
		return session.authenticate(credentials, ORGANIZACION)
				.map(usuario -> artefactoRepository.findByUsuario(artefactoIn, usuario))
				.map(artefacto -> {
					artefactoRepository.delete(artefacto);
					return ResponseEntity.ok("File was deleted successfully");
				}).orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}

	@GetMapping("{evidencia}/{artefacto}")
	public ResponseEntity<byte[]> getFile(@PathVariable("evidencia") Integer evidenciaIn,
										  @PathVariable("artefacto") Integer artefactoIn,
										  @RequestHeader("Credentials") Credentials credentials) {
		return session.authenticate(credentials, ORGANIZACION)
				.map(usuario -> artefactoRepository.findByUsuario(evidenciaIn, artefactoIn, usuario))
				.map(artefacto -> ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
						.body(artefacto.getArchivo())
				).orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}

	@GetMapping("{evidencia}")
	public ResponseEntity<List<Artefacto>> getAll(@PathVariable("evidencia") Integer evidenciaIn,
												  @RequestHeader("Credentials") Credentials credentials) {
		return session.authenticate(credentials, ORGANIZACION)
				.map(usuario -> artefactoRepository.findAllByUsuario(evidenciaIn, usuario))
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}
}
