package com.pi.appraisal.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import com.pi.appraisal.entity.Evidencia;
import com.pi.appraisal.entity.Usuario;

public interface EvidenciaRepository extends Repository<Evidencia, Integer> {
	@Query("select e from Evidencia e "
			+ "join Instancia i on e.instancia = i.evidencia "
			+ "join Organizacion o on i.organizacion = o.instancia "
			+ "where e.id = :evidencia.id and o.usuario = usuario.id")
	Evidencia getEvidenciaFromUsuario(Usuario usuario, Evidencia evidencia);
}
