package com.pi.appraisal.entity;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "Usuario", schema = "dbo", catalog = "Appraisal")
public class Usuario {

    private int id;
    private UsuarioRol usuarioRol;
    private Persona persona;
    private String username;
    private String password;
    private Organizacion organizacion;

    @Transient
    public UUID token;
    @Transient
    public UUID key;

    public Usuario() {
    }

    public Usuario(int id) {
        this.id = id;
    }

    public Usuario(int id, UsuarioRol usuarioRol, Persona persona, String username, String password) {
        this.id = id;
        this.usuarioRol = usuarioRol;
        this.persona = persona;
        this.username = username;
        this.password = password;
    }

    public Usuario(int id, UsuarioRol usuarioRol, Persona persona, String username, String password, Organizacion organizacion) {
        this.id = id;
        this.usuarioRol = usuarioRol;
        this.persona = persona;
        this.username = username;
        this.password = password;
        this.organizacion = organizacion;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rol", nullable = false)
    public UsuarioRol getUsuarioRol() {
        return this.usuarioRol;
    }

    public void setUsuarioRol(UsuarioRol usuarioRol) {
        this.usuarioRol = usuarioRol;
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "persona", nullable = false)
    public Persona getPersona() {
        return this.persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    @Column(name = "username", nullable = false)
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "password", nullable = false)
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "organizacion")
    public Organizacion getOrganizacion() {
        return this.organizacion;
    }

    public void setOrganizacion(Organizacion organizacion) {
        this.organizacion = organizacion;
    }

    public static class UsuarioImpl {
        public int id;
        public String username;
        public String password;
        public Persona.PersonaImpl persona;
        public Organizacion.OrganizacionImpl organizacion;
    }

    public static class SessionImpl {
        public String username;
        public SessionPersonaImpl persona;
        public SessionRolImpl usuarioRol;
        public Organizacion.OrganizacionImpl organizacion;
        public UUID token;
        public UUID key;

        public static SessionRolImpl to(UsuarioRol usuarioRol) {
            SessionRolImpl impl = new SessionRolImpl();
            impl.descripcion = usuarioRol.getDescripcion();
            return impl;
        }

        public static SessionPersonaImpl to(Persona persona) {
            SessionPersonaImpl impl = new SessionPersonaImpl();
            impl.nombre = persona.getNombre();
            impl.primerApellido = persona.getPrimerApellido();
            impl.segundoApellido = persona.getSegundoApellido();
            return impl;
        }

        public static class SessionRolImpl {
            public String descripcion;
        }

        public static class SessionPersonaImpl {
            public String nombre;
            public String primerApellido;
            public String segundoApellido;
        }
    }
}
