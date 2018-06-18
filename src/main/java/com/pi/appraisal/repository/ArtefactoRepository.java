package com.pi.appraisal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import com.pi.appraisal.entity.Artefacto;
import com.pi.appraisal.entity.Evidencia;
import com.pi.appraisal.entity.Usuario;

public interface ArtefactoRepository extends Repository<Artefacto, Integer> {
	Artefacto save(Artefacto artefacto);
	void delete(Artefacto artefacto);
	
	@Query("SELECT a FROM Artefacto a"
			+ "JOIN Evidencia e ON a.evidencia = e.id"
			+ "JOIN Instancia i ON e.instancia = i.id"
			+ "JOIN Organizacion o ON i.organizacion = o.id"
			+ "WHERE e.id = :evidencia.id AND o.usuario = :usuario.id AND a.id = :id AND a.nombre = :nombre")
	Artefacto findByUsuario(
			@Param("id") Integer id, 
			@Param("nombre") String nombre, 
			@Param("evidencia") Evidencia evidencia, 
			@Param("usuario") Usuario usuario
	);
	
	@Query("SELECT a FROM Artefacto a"
			+ "JOIN Evidencia e ON a.evidencia = e.id"
			+ "JOIN Instancia i ON e.instancia = i.id"
			+ "JOIN Organizacion o ON i.organizacion = o.id"
			+ "WHERE a.id = :artefacto.id AND o.usuario = :usuario.id")
	Artefacto findByUsuario(
			@Param("artefacto") Artefacto artefacto, 
			@Param("usuario") Usuario usuario
	);
	
	@Query("SELECT * FROM Artefacto a "
			+ "JOIN Evidencia e ON a.evidencia = e.id "
			+ "JOIN Instancia i ON e.instancia = i.id "
			+ "JOIN Organizacion o ON i.organizacion = o.id "
			+ "WHERE e.id = :evidencia.id AND o.usuario = :usuario.id")
	List<Artefacto> findAllByUsuario(
			@Param("evidencia") Evidencia evidencia, 
			@Param("usuario") Usuario usuario
	);
}
