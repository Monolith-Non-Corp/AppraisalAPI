package com.pi.appraisal.controller;

import com.pi.appraisal.component.SessionCache;
import com.pi.appraisal.entity.Usuario;
import com.pi.appraisal.entity.UsuarioRol;
import com.pi.appraisal.repository.UsuarioRepository;
import com.pi.appraisal.util.Credentials;
import org.apache.tomcat.util.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;
import java.util.regex.Pattern;

@RestController
@RequestMapping("api/session")
public class SessionController {

	/**
	 * Regex para la forma '(email)(:)(password)'
	 * <a href="http://emailregex.com/">Referencia</a>
	 */
	private static final String CREDENTIALS_MATCHER = "((?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)]))(:)([a-zA-Z!@#$%^&*:0-9]+)";
	private final UsuarioRepository usuarioRepository;
	private final SessionCache session;

	@Autowired
	public SessionController(UsuarioRepository usuarioRepository, SessionCache session) {
		this.usuarioRepository = usuarioRepository;
		this.session = session;
	}

	/**
	 * Inicializa la sesion del {@link Usuario} con su email y password.
	 * Asigna un token publico y privado el cual expira en 6 horas.
	 *
	 * @param credentials String codificado en base64 con la forma '(email)(:)(password)'
	 * @return {@link ResponseEntity} con el body del {@link Usuario} si es aplicable
	 */
	@GetMapping("login")
	public ResponseEntity<Usuario> login(@RequestHeader("Credentials") String credentials) {
		credentials = StringUtils.newStringUtf8(Base64.getDecoder().decode(credentials));                               //Convierte a String UTF-8 un base64 con el email y password
		if (Pattern.matches(CREDENTIALS_MATCHER, credentials)) {                                                        //Verifica que el String tenga la forma '(email)(:)(password)'
			String[] data = credentials.split(":", 2);                                                      //Separa el email y password
			return usuarioRepository.findByUsernameAndPassword(data[0], data[1])                                        //Busca el usuario con el email y password
					.map(usuario -> ResponseEntity.ok(session.init(usuario)))                                           //Si existe, inicializa el usuario con un token publico y privado
					.orElse(ResponseEntity.notFound().build());                                                         //Si no existe, envia un error
		} else return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();                                  //Si el String no es correcto, enviar error
	}

	/**
	 * Termina de sesion vinculada a las Credenciales recibidas
	 *
	 * @param credentials Objeto JSON {@link Credentials}
	 * @return {@link ResponseEntity} con el body {@link String} si es aplicable
	 */
	@GetMapping("logout")
	public ResponseEntity<String> logout(@RequestHeader("Credentials") Credentials credentials) {
		return session.authenticate(credentials, UsuarioRol.Priviledge.ANY)
				.map(usuario -> {
					session.remove(credentials.getToken());
					return ResponseEntity.ok("Logged out");
				}).orElse(ResponseEntity.status(HttpStatus.FORBIDDEN).build());
	}

	/**
	 * Valida que las {@link Credentials} esten activas en el servidor
	 *
	 * @param credentials Objeto JSON {@link Credentials}
	 * @return {@link ResponseEntity} con el body {@link Boolean} si es aplicable
	 */
	@GetMapping("validate")
	public ResponseEntity<Boolean> validate(@RequestHeader("Credentials") Credentials credentials) {
		return session.authenticate(credentials, UsuarioRol.Priviledge.ANY)                                             //Valida las credenciales
				.map(usuario -> ResponseEntity.ok(usuarioRepository.exists(usuario)))                                   //Si es valido, envia si el usuario existe
				.orElse(ResponseEntity.notFound().build());                                                             //Si no es valido, enviar error
	}
}
