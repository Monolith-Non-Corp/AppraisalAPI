package com.pi.appraisal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Organizacion", schema = "dbo", catalog = "Appraisal")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Organizacion {

	private int id;
	@JsonIgnore
	private Usuario usuario;
	private Nivel nivel;
	private String nombre;
	@JsonIgnore
	private Set<Instancia> instancias = new HashSet<>(0);

	public Organizacion() {
	}

	public Organizacion(int id, Usuario usuario, Nivel nivel, String nombre) {
		this.id = id;
		this.usuario = usuario;
		this.nivel = nivel;
		this.nombre = nombre;
	}

	public Organizacion(int id, Usuario usuario, Nivel nivel, String nombre, Set<Instancia> instancias) {
		this.id = id;
		this.usuario = usuario;
		this.nivel = nivel;
		this.nombre = nombre;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario", nullable = false)
	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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

	public interface OrganizacionImpl {
		int getId();

		Nivel getNivel();

		String getNombre();
	}
}
