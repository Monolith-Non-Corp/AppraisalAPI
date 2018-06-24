package com.pi.appraisal.controller;

import com.pi.appraisal.component.SessionCache;
import com.pi.appraisal.entity.Artefacto;
import com.pi.appraisal.entity.Evidencia;
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

	@PostMapping
	public ResponseEntity<Artefacto> upload(@RequestBody Evidencia in, @RequestParam("file") MultipartFile file,
											@RequestHeader("credentials") Credentials credentials) {
		return session.authenticate(credentials, ORGANIZACION)
				.map(usuario -> evidenciaRepository.findByUsuario(in, usuario)).map(evidencia -> {
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

	@DeleteMapping
	public ResponseEntity<String> delete(@RequestBody Artefacto in,
										 @RequestHeader("credentials") Credentials credentials) {
		return session.authenticate(credentials, ORGANIZACION)
				.map(usuario -> artefactoRepository.findByUsuario(in, usuario))
				.map(artefacto -> {
					artefactoRepository.delete(artefacto);
					return ResponseEntity.ok("File was deleted successfully");
				})
				.orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}

	@GetMapping("{id}/{name}")
	public ResponseEntity<byte[]> getFile(@PathVariable("id") Integer id, @PathVariable("name") String name,
										  @RequestBody() Evidencia in, @RequestHeader("credentials") Credentials credentials) {
		return session.authenticate(credentials, ORGANIZACION)
				.map(usuario -> artefactoRepository.findByUsuario(id, name, in, usuario))
				.map(artefacto -> ResponseEntity.ok()
						.contentType(MediaType.APPLICATION_OCTET_STREAM)
						.body(artefacto.getArchivo()))
				.orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}

	@GetMapping
	public ResponseEntity<List<Artefacto>> getAll(@RequestBody() Evidencia in,
												  @RequestHeader("credentials") Credentials credentials) {
		return session.authenticate(credentials, ORGANIZACION)
				.map(usuario -> artefactoRepository.findAllByUsuario(in, usuario))
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}
}
