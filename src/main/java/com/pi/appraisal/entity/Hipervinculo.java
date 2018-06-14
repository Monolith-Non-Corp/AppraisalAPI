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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Hipervinculo", schema = "dbo", catalog = "Appraisal")
public class Hipervinculo {

	private int id;
	private Evidencia evidencia;
	private String link;
	private Date fecha;

	public Hipervinculo() {}

	public Hipervinculo(int id, Evidencia evidencia, Date fecha) {
		this.id = id;
		this.evidencia = evidencia;
		this.fecha = fecha;
	}

	public Hipervinculo(int id, Evidencia evidencia, String link, Date fecha) {
		this.id = id;
		this.evidencia = evidencia;
		this.link = link;
		this.fecha = fecha;
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

	@Column(name = "link")
	public String getLink() {
		return this.link;
	}

	public void setLink(String link) {
		this.link = link;
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
