package com.pi.appraisal.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Organizacion", schema = "dbo", catalog = "Appraisal")
public class Organizacion {

    private int id;
    private Nivel nivel;
    private String nombre;
    private Set<Instancia> instancias = new HashSet<>(0);
    private Set<Usuario> usuarios = new HashSet<>(0);

    public Organizacion() {
    }

    public Organizacion(int id, Nivel nivel, String nombre) {
        this.id = id;
        this.nivel = nivel;
        this.nombre = nombre;
    }

    public Organizacion(int id, Nivel nivel, String nombre, Set<Instancia> instancias) {
        this.id = id;
        this.nivel = nivel;
        this.nombre = nombre;
        this.instancias = instancias;
    }

    public Organizacion(int id, Nivel nivel, String nombre, Set<Instancia> instancias, Set<Usuario> usuarios) {
        this.id = id;
        this.nivel = nivel;
        this.nombre = nombre;
        this.instancias = instancias;
        this.usuarios = usuarios;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nivel", nullable = false)
    public Nivel getNivel() {
        return this.nivel;
    }

    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
    }

    @Column(name = "nombre", nullable = false)
    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "organizacion")
    public Set<Instancia> getInstancias() {
        return this.instancias;
    }

    public void setInstancias(Set<Instancia> instancias) {
        this.instancias = instancias;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "organizacion")
    public Set<Usuario> getUsuarios() {
        return this.usuarios;
    }

    public void setUsuarios(Set<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public static class OrganizacionImpl {
        public int id;
        public Nivel.NivelImpl nivel;
        public String nombre;
    }
}
