package com.pi.appraisal.repository;

import java.util.List;

import org.springframework.data.repository.Repository;

import com.pi.appraisal.entity.AreaProceso;
import com.pi.appraisal.entity.Nivel;

public interface AreaProcesoRepository extends Repository<AreaProceso, Integer> {
	public List<AreaProceso.AreaProcesoImpl> findAllByNivel(Nivel nivel);
}
