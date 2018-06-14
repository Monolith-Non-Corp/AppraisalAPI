package com.pi.appraisal.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Instancia", schema = "dbo", catalog = "Appraisal")
public class Instancia {

	private int id;
	private Organizacion organizacion;
	private InstanciaTipo instanciaTipo;
	private String nombre;

	public Instancia() {}

	public Instancia(int id, Organizacion organizacion, InstanciaTipo instanciaTipo, String nombre) {
		this.id = id;
		this.organizacion = organizacion;
		this.instanciaTipo = instanciaTipo;
		this.nombre = nombre;
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
	@JoinColumn(name = "organizacion", nullable = false)
	public Organizacion getOrganizacion() {
		return this.organizacion;
	}

	public void setOrganizacion(Organizacion organizacion) {
		this.organizacion = organizacion;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "instancia_tipo", nullable = false)
	public InstanciaTipo getInstanciaTipo() {
		return this.instanciaTipo;
	}

	public void setInstanciaTipo(InstanciaTipo instanciaTipo) {
		this.instanciaTipo = instanciaTipo;
	}

	@Column(name = "nombre", nullable = false)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
