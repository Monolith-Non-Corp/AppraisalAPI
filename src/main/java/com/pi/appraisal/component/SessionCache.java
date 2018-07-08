package com.pi.appraisal.component;

import com.pi.appraisal.entity.Usuario;
import com.pi.appraisal.entity.UsuarioRol.Priviledge;
import com.pi.appraisal.util.Credentials;
import com.pi.appraisal.util.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.BiFunction;

@Component("session")
public class SessionCache {

    public static final long SESSION_TIMEOUT = 1000L * 60L * 60L * 6L; //Tiempo activo de una sesion (6 horas)
    public static final long REQUEST_TIMEOUT = 1000L * 60L * 15L; //Tiempo activo de un Http request (15 minutos)
    private static final BiFunction<UUID, Session, Session> FUNC = (i, session) -> {
        if (session != null) {                                                                                          //Si la sesion es asignada
            long oldTime = session.expires.getTime();                                                                   //Recuperar tiempo UTF de la ultima sesion
            long newTime = new Date().getTime();                                                                        //Recuperar tiempo UTF del sistema
            if (newTime - oldTime <= SessionCache.SESSION_TIMEOUT) {                                                    //Verificar que la sesion no exceda las 6 horas
                return session.renew();                                                                                 //Renovar sesion
            }
        }
        return null;                                                                                                    //Si no hay sesion o excede el tiempo, remover
    }; //Funcion de filtro de sesiones inactivas
    private Map<UUID, Session> cacheMap = new HashMap<>();
    private final SessionConverter converter;

    @Autowired
    public SessionCache(SessionConverter sessionConverter) {
        this.converter = sessionConverter;
    }

    /**
     * Inicializa la sesion del {@param usuario}
     *
     * @param usuario Un {@link Usuario}
     * @return El {@param usuario} con el token privado y publico
     */
    public Usuario init(Usuario usuario) {
        this.flush();                                                                                                   //Ejecutar filtro
        Session session = Session.of(usuario);                                                                          //Crear sesion
        this.cacheMap.put(session.token, session);                                                                      //Almacenar la sesion
        return usuario;                                                                                                 //Regresar el objeto usuario
    }

    /**
     * Termina la sesion con el token {@param uuid}
     *
     * @param json Las {@link Credentials} de la sesion
     */
    public void remove(String json) {
        Credentials credentials = converter.convert(json);
        if(credentials.getToken() != null) {
            this.cacheMap.remove(credentials.getToken());
        }
    }

    /**
     * Verifica que las {@param credentials} sean validas y que el usuario tenga el nivel de autorizacion indicado
     *
     * @param json Las {@link Credentials} de la sesion
     * @param priviledge  El {@link Priviledge} requerido
     * @return Un {@link Option} con el {@link Usuario} al que pertenece la sesion
     */
    public Option<Usuario> authenticate(String json, Priviledge priviledge) {
        Credentials credentials = converter.convert(json);
        if (credentials.isExpired())
            return Option.empty();                                                             //Si las credenciales hay caducado, regresar
        return getSession(credentials.getToken())                                                                       //Consigue la sesion almacenada
                .filter(session -> session.priviledge == priviledge || priviledge == Priviledge.ANY)                    //Verificar que la sesion tenga el privilegio requeridos
                .map(session -> {
                    String key = session.key.toString();
                    String token = session.token.toString();
                    String testHash = String.format("{%s}:{%s}:{%d}", key, token, credentials.getTimestamp());          //Crear hash
                    boolean match = false;
                    try {
                        Mac sha256 = Mac.getInstance("HmacSHA256");                                                     //Conseguir instancia de SHA-264
                        SecretKeySpec secret = new SecretKeySpec(key.getBytes(), "HmacSHA256");                      //Convertir token privado a secret key
                        sha256.init(secret);                                                                            //Inicializar secret key
                        byte[] bytes = Base64.getEncoder().encode(sha256.doFinal(testHash.getBytes()));                 //Convertir hash a bytes encriptados a String
                        String hash = new String(bytes);
                        match = credentials.hashEquals(hash);                                                           //Verificar si los hash coinciden
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return match ? new Usuario(session.userId) : null;                                                  //Si los hash coinciden, retorna el usuario con su id o nulo
                });
    }

    private Option<Session> getSession(UUID token) {
        this.flush();
        return Option.ofNullable(cacheMap.get(token));
    }

    private void flush() {                                                                                              //Por cada sesion, aplicar la funcion SessionCache.FUNC
        for (UUID uuid : cacheMap.keySet()) {
            cacheMap.compute(uuid, SessionCache.FUNC);
        }
    }

    private static class Session {

        UUID key; //Token privado
        UUID token; //Token publico
        Date expires; //Fecha de creacion de la sesion
        Integer userId; //Usuario al que pertenece la sesion
        Priviledge priviledge; //Privilegio del usuario/sesion

        Session(UUID key, UUID token, Integer userId, Date expires, Priviledge priviledge) {
            this.key = key;
            this.token = token;
            this.userId = userId;
            this.expires = expires;
            this.priviledge = priviledge;
        }

        /**
         * Renueva el tiempo de actividad de la sesion
         *
         * @return La {@link Session}
         */
        private Session renew() {
            this.expires = new Date();
            return this;
        }

        /**
         * Asigna el token privado y publico al {@param usuario}
         *
         * @param usuario Un {@link Usuario}
         * @return Una {@link Session} de usuario
         */
        private static Session of(Usuario usuario) {
            UUID token = UUID.randomUUID();                                                                             //Crea el token publico
            UUID key = UUID.randomUUID();                                                                               //Crea el token privado
            usuario.key = key;                                                                                          //Asigna el token privado al usuario
            usuario.token = token;                                                                                      //Asigna el token publico al usuario
            return new Session(key, token, usuario.getId(), new Date(), Priviledge.from(usuario.getUsuarioRol()));      //Crea una nueva sesion
        }
    }
}
