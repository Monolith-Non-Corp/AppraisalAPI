package com.pi.appraisal.repository;

import com.pi.appraisal.entity.UsuarioRol;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface UsuarioRolRepository extends Repository<UsuarioRol, Integer> {
    UsuarioRol findById(Integer id);

    List<UsuarioRol> findAll();

    default UsuarioRol getOrganizacion() {
        return findById(1);
    }

    default UsuarioRol getAdministrador() {
        return findById(2);
    }
}
