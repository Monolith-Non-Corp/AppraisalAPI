package com.pi.appraisal.repository;

import com.pi.appraisal.entity.MetaEspecifica;
import com.pi.appraisal.entity.PracticaEspecifica;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface PracticaEspecificaRepository extends Repository<PracticaEspecifica, Integer> {
    List<PracticaEspecifica> findAllByMetaEspecifica(MetaEspecifica metaEspecifica);

    Optional<PracticaEspecifica> findById(Integer id);
}
