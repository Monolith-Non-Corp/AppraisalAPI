package com.pi.appraisal.repository;

import com.pi.appraisal.entity.Organizacion;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface OrganizacionRepository extends Repository<Organizacion, Integer> {
    void delete(Organizacion organizacion);

    Optional<Organizacion> findById(Integer id);
}
