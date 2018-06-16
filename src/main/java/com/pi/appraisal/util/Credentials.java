package com.pi.appraisal.util;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import com.pi.appraisal.component.SessionCache;

public class Credentials {

	private UUID token;
	private Long timestamp;
	private String hash;

	public Credentials() {}

	public Credentials(UUID token, Long timestamp, String hash) {
		super();
		this.token = token;
		this.timestamp = timestamp;
		this.hash = hash;
	}
	
	public boolean isExpired() {
		long now = new Date().getTime();
		return now - timestamp > SessionCache.REQUEST_TIMEOUT;
	}
	
	public boolean hashEquals(String hash) {
		return Objects.equals(this.hash, hash);
	}

	public UUID getToken() {
		return token;
	}

	public void setToken(UUID token) {
		this.token = token;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}
	
}
