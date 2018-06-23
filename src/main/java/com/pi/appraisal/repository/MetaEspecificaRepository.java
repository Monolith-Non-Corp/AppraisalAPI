package com.pi.appraisal.repository;

import java.util.List;

import org.springframework.data.repository.Repository;

import com.pi.appraisal.entity.AreaProceso;
import com.pi.appraisal.entity.MetaEspecifica;

public interface MetaEspecificaRepository extends Repository<MetaEspecifica, Integer> {
	List<MetaEspecifica.MetaEspecificaImpl> findAllByAreaProceso(AreaProceso area);
}
