package com.pi.appraisal.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pi.appraisal.component.SessionCache;
import com.pi.appraisal.entity.Artefacto;
import com.pi.appraisal.entity.Evidencia;
import static com.pi.appraisal.entity.UsuarioRol.Priviledge.*;
import com.pi.appraisal.repository.ArtefactoRepository;
import com.pi.appraisal.repository.EvidenciaRepository;
import com.pi.appraisal.util.Credentials;

@RestController
@RequestMapping("api/file")
public class ArtefactoController {

	@Autowired
	private ArtefactoRepository artefactoRepository;
	@Autowired
	private EvidenciaRepository evidenciaRepository;
	@Autowired
	private SessionCache session;

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
				.map(list -> ResponseEntity.ok(list))
				.orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}
}
