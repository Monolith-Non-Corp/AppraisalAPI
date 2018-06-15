package com.pi.appraisal.component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiFunction;

import org.springframework.stereotype.Component;

import com.pi.appraisal.entity.Usuario;
import com.pi.appraisal.entity.UsuarioRol.Priviledge;
import com.pi.appraisal.util.Credentials;

@Component("session")
public class SessionCache {

	public static final long SESSION_TIMEOUT = 1000L * 60L * 60L;
	public static final long REQUEST_TIMEOUT = 1000L * 10L;
	private static final BiFunction<UUID, Session, Session> FUNC = (i, session) -> {
		if (session != null) {
			long oldTime = session.expires.getTime();
			long newTime = new Date().getTime();
			if (newTime - oldTime <= SessionCache.SESSION_TIMEOUT) {
				return session.renew();
			}
		}
		return null;
	};

	private Map<UUID, Session> cacheMap = new HashMap<>();

	public Usuario init(Usuario usuario) {
		this.flush();
		UUID token = UUID.randomUUID();
		this.cacheMap.putIfAbsent(token, Session.of(token, usuario));
		return usuario;
	}

	public Optional<Priviledge> authenticate(Credentials credentials, String preHash) {
		return getSession(credentials.getToken()).filter(ignored -> !credentials.isExpired()).map(session -> {
			String testHash = session.key.toString() + ":" + preHash;
			boolean match = false;
			try {
				MessageDigest digest = MessageDigest.getInstance("SHA-256");
				byte[] bytes = digest.digest(testHash.getBytes(StandardCharsets.UTF_8));
				String hash = Base64.getEncoder().encodeToString(bytes);
				match = credentials.isHash(hash);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return match ? session.priviledge : null;
		});
	}

	private Optional<Session> getSession(UUID token) {
		this.flush();
		return Optional.ofNullable(cacheMap.get(token));
	}

	private void flush() {
		Iterator<UUID> it = cacheMap.keySet().iterator();
		while (it.hasNext()) {
			cacheMap.compute(it.next(), SessionCache.FUNC);
		}
	}

	private static class Session {

		UUID key;
		Date expires;
		Priviledge priviledge;

		Session(UUID key, Date expires, Priviledge priviledge) {
			this.key = key;
			this.expires = expires;
			this.priviledge = priviledge;
		}

		private Session renew() {
			this.expires = new Date();
			return this;
		}

		private static Session of(UUID token, Usuario usuario) {
			UUID key = UUID.randomUUID();
			usuario.setKey(key);
			usuario.setToken(token);
			return new Session(key, new Date(), Priviledge.from(usuario.getUsuarioRol()));
		}
	}
}
