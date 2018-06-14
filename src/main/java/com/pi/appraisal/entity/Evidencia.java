package com.pi.appraisal.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Evidencia", schema = "dbo", catalog = "Appraisal")
public class Evidencia {

	private int id;
	private PracticaEspecifica practicaEspecifica;
	private int instancia;
	private Set<Artefacto> artefactos = new HashSet<>(0);
	private Set<Hipervinculo> hipervinculos = new HashSet<>(0);

	public Evidencia() {}

	public Evidencia(int id, PracticaEspecifica practicaEspecifica, int instancia) {
		this.id = id;
		this.practicaEspecifica = practicaEspecifica;
		this.instancia = instancia;
	}

	public Evidencia(int id, PracticaEspecifica practicaEspecifica, int instancia, Set<Artefacto> artefactos, Set<Hipervinculo> hipervinculos) {
		this.id = id;
		this.practicaEspecifica = practicaEspecifica;
		this.instancia = instancia;
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
	@JoinColumn(name = "practica_especifica", nullable = false)
	public PracticaEspecifica getPracticaEspecifica() {
		return this.practicaEspecifica;
	}

	public void setPracticaEspecifica(PracticaEspecifica practicaEspecifica) {
		this.practicaEspecifica = practicaEspecifica;
	}

	@Column(name = "instancia", nullable = false)
	public int getInstancia() {
		return this.instancia;
	}

	public void setInstancia(int instancia) {
		this.instancia = instancia;
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
