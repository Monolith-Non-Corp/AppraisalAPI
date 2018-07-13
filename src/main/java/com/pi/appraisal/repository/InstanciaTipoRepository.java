package com.pi.appraisal.repository;

import com.pi.appraisal.entity.InstanciaTipo;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface InstanciaTipoRepository extends Repository<InstanciaTipo, Integer> {
    Optional<InstanciaTipo> findById(Integer id);

    List<InstanciaTipo> findAll();
}
