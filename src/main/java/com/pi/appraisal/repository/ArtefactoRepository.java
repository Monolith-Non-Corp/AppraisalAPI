package com.pi.appraisal.repository;

import java.util.Optional;

import org.springframework.data.repository.Repository;

import com.pi.appraisal.entity.Artefacto;

public interface ArtefactoRepository extends Repository<Artefacto, Integer> {
	Artefacto save(Artefacto artefacto);
	void delete(Artefacto artefacto);
	
	Optional<Artefacto> findByIdAndNombre(Integer id, String nombre);
}
