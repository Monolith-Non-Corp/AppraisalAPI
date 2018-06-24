package com.pi.appraisal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Usuario_Rol", schema = "dbo", catalog = "Appraisal")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UsuarioRol {

	private int id;
	private String descripcion;
	private Set<Usuario> usuarios = new HashSet<>(0);

	public UsuarioRol() {
	}

	public UsuarioRol(int id, String descripcion) {
		this.id = id;
		this.descripcion = descripcion;
	}

	public UsuarioRol(int id, String descripcion, Set<Usuario> usuarios) {
		this.id = id;
		this.descripcion = descripcion;
		this.usuarios = usuarios;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usuarioRol")
	@JsonIgnore
	public Set<Usuario> getUsuarios() {
		return this.usuarios;
	}

	public void setUsuarios(Set<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public enum Priviledge {
		ORGANIZACION, ADMINISTRADOR, ANY;

		public static Priviledge from(UsuarioRol rol) {
			switch (rol.id) {
				case 1:
					return ORGANIZACION;
				case 2:
					return ADMINISTRADOR;
				default:
					return ANY;
			}
		}
	}
}
