package com.pi.appraisal.util;

import com.pi.appraisal.component.SessionCache;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/**
 * Credenciales del {@link com.pi.appraisal.entity.Usuario} incluido en el Header del Http request
 */
public class Credentials {

    private UUID token;                                                                                                 //Token publico
    private String hash;                                                                                                //Hash del token privado + timestamp
    private Long timestamp;                                                                                             //Tiempo UTF del Http request al enviarse

    public Credentials() {
    }

    public Credentials(UUID token, Long timestamp, String hash) {
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

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

}
