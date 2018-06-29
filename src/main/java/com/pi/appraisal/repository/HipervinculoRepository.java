package com.pi.appraisal.repository;

import com.pi.appraisal.entity.Hipervinculo;
import com.pi.appraisal.entity.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HipervinculoRepository extends Repository<Hipervinculo, Integer> {
	Hipervinculo save(Hipervinculo hipervinculo);

	void delete(Hipervinculo hipervinculo);

	@Query("SELECT h FROM Hipervinculo h"
			+ " JOIN Evidencia e ON h.evidencia = e.id"
			+ " JOIN Instancia i ON e.instancia = i.id "
			+ " JOIN Organizacion o ON i.organizacion = o.id "
			+ " WHERE e.id = :evidencia AND o.usuario = :usuario AND h.id = :hipervinculo")
	Hipervinculo findByUsuario(
			@Param("hipervinculo") Integer hipervinculo,
			@Param("usuario") Usuario usuario
	);

	@Query("SELECT h FROM Hipervinculo h"
			+ " JOIN Evidencia e ON h.evidencia = e.id"
			+ " JOIN Instancia i ON e.instancia = i.id "
			+ " JOIN Organizacion o ON i.organizacion = o.id "
			+ " WHERE i.id = :instanciaIn AND o.usuario = :usuario AND h.id = :hipervinculo")
	List<Hipervinculo> findAllByUsuario(
			@Param("instanciaIn") Integer instanciaIn,
			@Param("usuario") Usuario usuario
	);
}
