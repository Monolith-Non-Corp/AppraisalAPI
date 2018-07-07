package com.pi.appraisal.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Area_Proceso", schema = "dbo", catalog = "Appraisal")
public class AreaProceso {

    private int id;
    private Nivel nivel;
    private Categoria categoria;
    private String nombre;
    private String descripcion;
    private Set<MetaEspecifica> metaEspecificas = new HashSet<>(0);

    public AreaProceso() {
    }

    public AreaProceso(int id) {
        this.id = id;
    }

    public AreaProceso(int id, Nivel nivel, Categoria categoria, String nombre, String descripcion) {
        this.id = id;
        this.nivel = nivel;
        this.categoria = categoria;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public AreaProceso(int id, Nivel nivel, Categoria categoria, String nombre, String descripcion, Set<MetaEspecifica> metaEspecificas) {
        this.id = id;
        this.nivel = nivel;
        this.categoria = categoria;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.metaEspecificas = metaEspecificas;
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
    @JoinColumn(name = "nivel", nullable = false)
    public Nivel getNivel() {
        return this.nivel;
    }

    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria", nullable = false)
    public Categoria getCategoria() {
        return this.categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
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

    public void setDescripcion(String desc) {
        this.descripcion = desc;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "areaProceso")
    public Set<MetaEspecifica> getMetaEspecificas() {
        return this.metaEspecificas;
    }

    public void setMetaEspecificas(Set<MetaEspecifica> metaEspecificas) {
        this.metaEspecificas = metaEspecificas;
    }

    public static class AreaProcesoImpl {
        public int id;
        public String nombre;
        public String descripcion;
        public Categoria.CategoriaImpl categoria;
    }
}
