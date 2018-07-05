package com.pi.appraisal.repository;

import com.pi.appraisal.entity.Nivel;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface NivelRepository extends Repository<Nivel, Integer> {
    Optional<Nivel> findByLvl(int lvl);
}
