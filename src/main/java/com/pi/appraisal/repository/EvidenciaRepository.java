package com.pi.appraisal.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import com.pi.appraisal.entity.Evidencia;
import com.pi.appraisal.entity.Usuario;

public interface EvidenciaRepository extends Repository<Evidencia, Integer> {
	Evidencia save(Evidencia evidencia);
	void delete(Evidencia evidencia);
	
	@Query("SELECT e FROM Evidencia e "
			+ "JOIN Instancia i ON e.instancia = i.id "
			+ "JOIN Organizacion o ON i.organizacion = o.id "
			+ "WHERE e.id = :evidencia.id AND o.usuario = :usuario.id")
	Evidencia findByUsuario(
			@Param("evidencia") Evidencia evidencia, 
			@Param("usuario") Usuario usuario
	);
}
