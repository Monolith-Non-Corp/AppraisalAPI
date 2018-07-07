package com.pi.appraisal.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "Usuario", schema = "dbo", catalog = "Appraisal")
public class Usuario {

    private int id;
    private UsuarioRol usuarioRol;
    private Persona persona;
    private String username;
    private String password;
    private Set<Organizacion> organizacions = new HashSet<>(0);

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

    public Usuario(int id, UsuarioRol usuarioRol, Persona persona, String username, String password, Set<Organizacion> organizacions) {
        this.id = id;
        this.usuarioRol = usuarioRol;
        this.persona = persona;
        this.username = username;
        this.password = password;
        this.organizacions = organizacions;
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

    @ManyToOne(fetch = FetchType.LAZY)
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario")
    public Set<Organizacion> getOrganizacions() {
        return this.organizacions;
    }

    public void setOrganizacions(Set<Organizacion> organizacions) {
        this.organizacions = organizacions;
    }

    public static class UsuarioImpl {
        public int id;
        public String username;
        public Persona.PersonaImpl persona;
        public UsuarioRol.UsuarioRolImpl usuarioRol;
        public UUID token;
        public UUID key;
    }
}
