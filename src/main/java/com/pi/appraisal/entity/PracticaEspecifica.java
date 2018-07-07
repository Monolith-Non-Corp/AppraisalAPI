package com.pi.appraisal.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Practica_Especifica", schema = "dbo", catalog = "Appraisal")
public class PracticaEspecifica {

    private int id;
    private MetaEspecifica metaEspecifica;
    private String nombre;
    private String descripcion;
    private Set<Evidencia> evidencias = new HashSet<>(0);

    public PracticaEspecifica() {
    }

    public PracticaEspecifica(int id, MetaEspecifica metaEspecifica, String nombre, String descripcion) {
        this.id = id;
        this.metaEspecifica = metaEspecifica;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public PracticaEspecifica(int id, MetaEspecifica metaEspecifica, String nombre, String descripcion, Set<Evidencia> evidencias) {
        this.id = id;
        this.metaEspecifica = metaEspecifica;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.evidencias = evidencias;
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
    @JoinColumn(name = "meta_especifica", nullable = false)
    public MetaEspecifica getMetaEspecifica() {
        return this.metaEspecifica;
    }

    public void setMetaEspecifica(MetaEspecifica metaEspecifica) {
        this.metaEspecifica = metaEspecifica;
    }

    @Column(name = "nombre", nullable = false)
    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Column(name = "descripcion", nullable = false)
    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "practicaEspecifica")
    public Set<Evidencia> getEvidencias() {
        return this.evidencias;
    }

    public void setEvidencias(Set<Evidencia> evidencias) {
        this.evidencias = evidencias;
    }

    public static class PracticaEspecificaImpl {
        public int id;
        public String nombre;
        public String descripcion;
    }
}
