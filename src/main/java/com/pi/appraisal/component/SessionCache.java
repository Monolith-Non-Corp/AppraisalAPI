package com.pi.appraisal.component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiFunction;

import org.springframework.stereotype.Component;

import com.pi.appraisal.entity.Usuario;
import com.pi.appraisal.entity.UsuarioRol.Priviledge;
import com.pi.appraisal.util.Credentials;
import com.pi.appraisal.util.Option;

@Component("session")
public class SessionCache {

	public static final long SESSION_TIMEOUT = 1000L * 60L * 60L * 6L;
	public static final long REQUEST_TIMEOUT = 1000L * 60L * 15L;
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
		Session session  = Session.of(usuario);
		this.cacheMap.put(session.token, session);
		return usuario;
	}

	public Option<Usuario> authenticate(Credentials credentials, Priviledge priviledge) {
		if (credentials.isExpired()) return Option.empty();
		return getSession(credentials.getToken()).filter(session -> session.priviledge == priviledge).map(session -> {
			String testHash = String.format("{%s}:{%d}", session.key.toString(), credentials.getTimestamp());
			boolean match = false;
			try {
				MessageDigest digest = MessageDigest.getInstance("SHA-256");
				byte[] bytes = digest.digest(testHash.getBytes(StandardCharsets.UTF_8));
				String hash = Base64.getEncoder().encodeToString(bytes);
				match = credentials.hashEquals(hash);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return match ? new Usuario(session.userId) : null;
		});
	}

	private Option<Session> getSession(UUID token) {
		this.flush();
		return Option.ofNullable(cacheMap.get(token));
	}

	private void flush() {
		for (UUID uuid : cacheMap.keySet()) {
			cacheMap.compute(uuid, SessionCache.FUNC);
		}
	}

	private static class Session {

		UUID key;
		UUID token;
		Date expires;
		Integer userId;
		Priviledge priviledge;

		Session(UUID key, UUID token, Integer userId, Date expires, Priviledge priviledge) {
			this.key = key;
			this.token = token;
			this.userId = userId;
			this.expires = expires;
			this.priviledge = priviledge;
		}

		private Session renew() {
			this.expires = new Date();
			return this;
		}

		private static Session of(Usuario usuario) {
			UUID token = UUID.randomUUID();
			UUID key = UUID.randomUUID();
			usuario.setKey(key);
			usuario.setToken(token);
			return new Session(key, token, usuario.getId(), new Date(), Priviledge.from(usuario.getUsuarioRol()));
		}
	}
}
