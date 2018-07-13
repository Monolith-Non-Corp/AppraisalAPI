package com.pi.appraisal.repository;

import com.pi.appraisal.entity.Organizacion;
import com.pi.appraisal.entity.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OrganizacionRepository extends Repository<Organizacion, Integer> {
    Organizacion save(Organizacion organizacion);

    void delete(Organizacion organizacion);

    Optional<Organizacion> findById(Integer id);

    @Query("SELECT o FROM Organizacion o"
            + " JOIN Usuario u ON u.organizacion = o.id"
            + " WHERE o.id = :organizacion AND u.id = :usuario")
    Organizacion findByUsuario(
            @Param("organizacion") Integer organizacion,
            @Param("usuario") Integer usuario
    );
}
