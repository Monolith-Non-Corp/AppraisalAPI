package com.pi.appraisal.repository;

import java.util.Optional;

import org.springframework.data.repository.Repository;

import com.pi.appraisal.entity.Nivel;

public interface NivelRepository extends Repository<Nivel, Integer> {
	public Optional<Nivel.NivelImpl> findByLvl(int lvl);
}
