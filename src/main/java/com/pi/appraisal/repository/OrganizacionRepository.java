package com.pi.appraisal.repository;

import com.pi.appraisal.entity.Organizacion;
import com.pi.appraisal.entity.Usuario;
import org.springframework.data.repository.Repository;

public interface OrganizacionRepository extends Repository<Organizacion, Integer> {
    Organizacion save(Organizacion organizacion);

    void delete(Organizacion organizacion);

    Organizacion findByIdAndUsuario(Integer id, Usuario usuario);
}
