package com.pi.appraisal.controller;

import java.io.IOException;

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
import com.pi.appraisal.entity.UsuarioRol.Priviledge;
import com.pi.appraisal.repository.ArtefactoRepository;

@RestController
@RequestMapping("api/file")
public class ArtefactoController {

	@Autowired
	private ArtefactoRepository artefactoRepository;
	@Autowired
	private SessionCache session;

	@PostMapping
	public ResponseEntity<String> upload(@RequestBody Evidencia evidencia, @RequestParam("file") MultipartFile file,
			@RequestHeader("token") String token, @RequestHeader("hash") String hash) {
		if (!file.isEmpty()) {
			return session.authenticate(token, "{evidencia}/{file}/{token}/{hash}", hash)
					.filter(priviledge -> priviledge == Priviledge.ORGANIZACION).map(ignored -> {
						try {
							Artefacto artefacto = new Artefacto();
							artefacto.setArchivo(file.getBytes());
							artefacto.setNombre(file.getOriginalFilename());
							artefacto.setTipo(file.getContentType());
							artefactoRepository.save(artefacto);
						} catch (IOException e) {
							e.printStackTrace();
							return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
									.body("Unable to read File bytes");
						}
						return ResponseEntity.ok("File was uploaded successfully");
					}).orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
		} else return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File is empty or has no content");
	}
	
	@DeleteMapping
	public ResponseEntity<String> delete(@RequestBody Artefacto artefacto,
			@RequestHeader("token") String token, @RequestHeader("hash") String hash) {
			return session.authenticate(token, "{evidencia}/{file}/{token}/{hash}", hash)
					.filter(priviledge -> priviledge == Priviledge.ORGANIZACION)
					.map(ignored -> {
						artefactoRepository.delete(artefacto);
						return ResponseEntity.ok("File was uploaded successfully");
					}).orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}

	@GetMapping("{id}/{name}")
	public ResponseEntity<byte[]> getFile(@PathVariable("id") Integer id, @PathVariable("name") String name,
			@RequestHeader("token") String token, @RequestHeader("hash") String hash) {
		return session.authenticate(token, "{id}/{name}/{token}/{hash}", hash)
				.filter(priviledge -> priviledge == Priviledge.ORGANIZACION)
				.map(ignored -> artefactoRepository.findByIdAndNombre(id, name).orElse(null))
				.map(a -> ResponseEntity.ok().contentType(MediaType.valueOf(a.getTipo())).body(a.getArchivo()))
				.orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}
}
