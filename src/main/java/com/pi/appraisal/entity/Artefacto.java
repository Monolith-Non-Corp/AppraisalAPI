package com.pi.appraisal.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Artefacto", schema = "dbo", catalog = "Appraisal")
public class Artefacto {

	private int id;
	private Evidencia evidencia;
	private byte[] archivo;
	private String nombre;
	private String tipo;
	private Date fecha;

	public Artefacto() {}

	public Artefacto(int id, Evidencia evidencia, byte[] archivo, String nombre, Date fecha) {
		this.id = id;
		this.evidencia = evidencia;
		this.archivo = archivo;
		this.nombre = nombre;
		this.fecha = fecha;
	}

	public Artefacto(int id, Evidencia evidencia, byte[] archivo, String nombre, String tipo, Date fecha) {
		this.id = id;
		this.evidencia = evidencia;
		this.archivo = archivo;
		this.nombre = nombre;
		this.tipo = tipo;
		this.fecha = fecha;
	}
	
	@PrePersist
	protected void onCreate() {
		fecha = new Date();
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
	@JoinColumn(name = "evidencia", nullable = false)
	public Evidencia getEvidencia() {
		return this.evidencia;
	}

	public void setEvidencia(Evidencia evidencia) {
		this.evidencia = evidencia;
	}

	@Column(name = "archivo", nullable = false)
	public byte[] getArchivo() {
		return this.archivo;
	}

	public void setArchivo(byte[] archivo) {
		this.archivo = archivo;
	}

	@Column(name = "nombre", nullable = false)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "tipo")
	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha", nullable = false, length = 27)
	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

}
