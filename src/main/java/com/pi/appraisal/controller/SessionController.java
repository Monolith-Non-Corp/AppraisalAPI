package com.pi.appraisal.controller;

import java.util.Base64;
import java.util.regex.Pattern;

import org.apache.tomcat.util.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pi.appraisal.component.SessionCache;
import com.pi.appraisal.entity.Usuario;
import com.pi.appraisal.repository.UsuarioRepository;

@RestController
@RequestMapping("api/session")
public class SessionController {

	private static final String CREDENTIALS_MATCHER = "((?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)]))(:)([a-zA-Z!@#$%^&*:0-9]+)";

	private final UsuarioRepository usuarioRepository;
	private final SessionCache session;

	@Autowired
	public SessionController(UsuarioRepository usuarioRepository, SessionCache session) {
		this.usuarioRepository = usuarioRepository;
		this.session = session;
	}

	@GetMapping
	public ResponseEntity<Usuario> getUser(@RequestHeader("credentials") String credentials) {
		credentials = StringUtils.newStringUtf8(Base64.getDecoder().decode(credentials));
		if (Pattern.matches(CREDENTIALS_MATCHER, credentials)) {
			String[] data = credentials.split(":", 2);
			return usuarioRepository.findByUsernameAndPassword(data[0], data[1])
					.map(usuario -> ResponseEntity.ok(session.init(usuario)))
					.orElse(ResponseEntity.notFound().build());
		} else return ResponseEntity.notFound().build();
	}
}
