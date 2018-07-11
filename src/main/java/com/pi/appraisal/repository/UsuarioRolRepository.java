package com.pi.appraisal.repository;

import com.pi.appraisal.entity.UsuarioRol;
import org.springframework.data.repository.Repository;

public interface UsuarioRolRepository extends Repository<UsuarioRol, Integer> {
    UsuarioRol findById(Integer id);

    default UsuarioRol getOrganizacion() {
        return findById(1);
    }
}
