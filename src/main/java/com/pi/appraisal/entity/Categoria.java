package com.pi.appraisal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Categoria", schema = "dbo", catalog = "Appraisal")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Categoria {

    private int id;
    private String nombre;
    @JsonIgnore
    private Set<AreaProceso> areaProcesos = new HashSet<>(0);

    public Categoria() {
    }

    public Categoria(int id) {
        this.id = id;
    }

    public Categoria(int id, String nombre, Set<AreaProceso> areaProcesos) {
        this.id = id;
        this.nombre = nombre;
        this.areaProcesos = areaProcesos;
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

    @Column(name = "nombre")
    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "categoria")
    public Set<AreaProceso> getAreaProcesos() {
        return this.areaProcesos;
    }

    public void setAreaProcesos(Set<AreaProceso> areaProcesos) {
        this.areaProcesos = areaProcesos;
    }

    public interface CategoriaImpl {
        String getNombre();

        int getId();
    }
}
