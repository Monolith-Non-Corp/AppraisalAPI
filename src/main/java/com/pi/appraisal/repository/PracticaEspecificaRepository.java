package com.pi.appraisal.repository;

import com.pi.appraisal.entity.MetaEspecifica;
import com.pi.appraisal.entity.PracticaEspecifica;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface PracticaEspecificaRepository extends Repository<PracticaEspecifica, Integer> {
    List<PracticaEspecifica> findAllByMetaEspecifica(MetaEspecifica metaEspecifica);
}
