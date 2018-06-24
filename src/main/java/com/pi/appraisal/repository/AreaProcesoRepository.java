package com.pi.appraisal.repository;

import com.pi.appraisal.entity.AreaProceso;
import com.pi.appraisal.entity.Nivel;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface AreaProcesoRepository extends Repository<AreaProceso, Integer> {
	List<AreaProceso.AreaProcesoImpl> findAllByNivel(Nivel nivel);
}
