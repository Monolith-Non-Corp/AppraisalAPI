package com.pi.appraisal.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "Instancia_Tipo", schema = "dbo", catalog = "Appraisal")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class InstanciaTipo {

	private int id;
	private String descripcion;
	private Set<Instancia> instancias = new HashSet<>(0);

	public InstanciaTipo() {}

	public InstanciaTipo(int id, String descripcion) {
		this.id = id;
		this.descripcion = descripcion;
	}

	public InstanciaTipo(int id, String descripcion, Set<Instancia> instancias) {
		this.id = id;
		this.descripcion = descripcion;
		this.instancias = instancias;
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

	@Column(name = "descripcion", nullable = false)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "instanciaTipo")
	public Set<Instancia> getInstancias() {
		return this.instancias;
	}

	public void setInstancias(Set<Instancia> instancias) {
		this.instancias = instancias;
	}

}
