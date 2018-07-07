package com.pi.appraisal.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Meta_Especifica", schema = "dbo", catalog = "Appraisal")
public class MetaEspecifica {

    private int id;
    private AreaProceso areaProceso;
    private String nombre;
    private String descripcion;
    private Set<PracticaEspecifica> practicaEspecificas = new HashSet<>(0);

    public MetaEspecifica() {
    }

    public MetaEspecifica(int id) {
        this.id = id;
    }

    public MetaEspecifica(int id, AreaProceso areaProceso, String nombre, String descripcion) {
        this.id = id;
        this.areaProceso = areaProceso;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public MetaEspecifica(int id, AreaProceso areaProceso, String nombre, String descripcion, Set<PracticaEspecifica> practicaEspecificas) {
        this.id = id;
        this.areaProceso = areaProceso;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.practicaEspecificas = practicaEspecificas;
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
    @JoinColumn(name = "area_proceso", nullable = false)
    public AreaProceso getAreaProceso() {
        return this.areaProceso;
    }

    public void setAreaProceso(AreaProceso areaProceso) {
        this.areaProceso = areaProceso;
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "metaEspecifica")
    public Set<PracticaEspecifica> getPracticaEspecificas() {
        return this.practicaEspecificas;
    }

    public void setPracticaEspecificas(Set<PracticaEspecifica> practicaEspecificas) {
        this.practicaEspecificas = practicaEspecificas;
    }

    public static class MetaEspecificaImpl {
        public int id;
        public String nombre;
        public String descripcion;
    }
}
