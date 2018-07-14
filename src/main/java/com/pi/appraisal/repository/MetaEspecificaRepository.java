package com.pi.appraisal.repository;

import com.pi.appraisal.entity.AreaProceso;
import com.pi.appraisal.entity.MetaEspecifica;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface MetaEspecificaRepository extends Repository<MetaEspecifica, Integer> {
    List<MetaEspecifica> findAllByAreaProceso(AreaProceso areaProceso);

    Optional<MetaEspecifica> findById(Integer id);
}
