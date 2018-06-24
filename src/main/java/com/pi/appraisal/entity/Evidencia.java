package com.pi.appraisal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Evidencia", schema = "dbo", catalog = "Appraisal")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Evidencia {

	private int id;
	@JsonIgnore
	private Instancia instancia;
	@JsonIgnore
	private PracticaEspecifica practicaEspecifica;
	private Set<Artefacto> artefactos = new HashSet<>(0);
	private Set<Hipervinculo> hipervinculos = new HashSet<>(0);

	public Evidencia() {
	}

	public Evidencia(Instancia instancia, PracticaEspecifica practicaEspecifica) {
		this.instancia = instancia;
		this.practicaEspecifica = practicaEspecifica;
	}

	public Evidencia(Instancia instancia, PracticaEspecifica practicaEspecifica, Set<Artefacto> artefactos, Set<Hipervinculo> hipervinculos) {
		this.instancia = instancia;
		this.practicaEspecifica = practicaEspecifica;
		this.artefactos = artefactos;
		this.hipervinculos = hipervinculos;
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
	@JoinColumn(name = "instancia", nullable = false)
	public Instancia getInstancia() {
		return this.instancia;
	}

	public void setInstancia(Instancia instancia) {
		this.instancia = instancia;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "practica_especifica", nullable = false)
	public PracticaEspecifica getPracticaEspecifica() {
		return this.practicaEspecifica;
	}

	public void setPracticaEspecifica(PracticaEspecifica practicaEspecifica) {
		this.practicaEspecifica = practicaEspecifica;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "evidencia")
	public Set<Artefacto> getArtefactos() {
		return this.artefactos;
	}

	public void setArtefactos(Set<Artefacto> artefactos) {
		this.artefactos = artefactos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "evidencia")
	public Set<Hipervinculo> getHipervinculos() {
		return this.hipervinculos;
	}

	public void setHipervinculos(Set<Hipervinculo> hipervinculos) {
		this.hipervinculos = hipervinculos;
	}

}
