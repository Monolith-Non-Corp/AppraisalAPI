package com.pi.appraisal.repository;

import java.util.List;

import org.springframework.data.repository.Repository;

import com.pi.appraisal.entity.MetaEspecifica;
import com.pi.appraisal.entity.PracticaEspecifica;

public interface PracticaEspecificaRepository extends Repository<PracticaEspecifica, Integer> {
	List<PracticaEspecifica.PracticaEspecificaImpl> findAllByMetaEspecifica(MetaEspecifica meta);
}
