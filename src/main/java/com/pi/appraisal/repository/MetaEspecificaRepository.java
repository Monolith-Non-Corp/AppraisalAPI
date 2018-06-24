package com.pi.appraisal.repository;

import com.pi.appraisal.entity.AreaProceso;
import com.pi.appraisal.entity.MetaEspecifica;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface MetaEspecificaRepository extends Repository<MetaEspecifica, Integer> {
	List<MetaEspecifica.MetaEspecificaImpl> findAllByAreaProceso(AreaProceso area);
}
