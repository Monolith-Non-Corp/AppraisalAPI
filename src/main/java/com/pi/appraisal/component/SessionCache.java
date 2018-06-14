package com.pi.appraisal.component;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiFunction;

import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import com.pi.appraisal.entity.Usuario;

@Component("session")
public class SessionCache {

	private static final long TIMEOUT = 1000L * 60L * 60L;
	private static final BiFunction<UUID, Pair<UUID, Date>, Pair<UUID, Date>> FUNC = (i, p) -> {
		if (p != null) {
			long oldTime = p.getSecond().getTime();
			long newTime = new Date().getTime();
			if (newTime - oldTime <= SessionCache.TIMEOUT) {
				return Pair.of(p.getFirst(), new Date());
			}
		}
		return null;
	};

	private Map<UUID, Pair<UUID, Date>> cacheMap = new HashMap<>();

	public Usuario init(Usuario usuario) {
		this.flush();
		UUID token = UUID.randomUUID();
		UUID key = UUID.randomUUID();
		usuario.setKey(key);
		usuario.setToken(token);
		this.cacheMap.putIfAbsent(token, Pair.of(token, new Date()));
		return usuario;
	}

	public Optional<UUID> getKey(UUID token) {
		this.flush();
		return cacheMap.containsKey(token) ? Optional.of(cacheMap.get(token).getFirst()) : Optional.empty();
	}

	public void flush() {
		Iterator<UUID> it = cacheMap.keySet().iterator();
		while(it.hasNext()) {
			cacheMap.compute(it.next(), SessionCache.FUNC);
		}
	}
}
