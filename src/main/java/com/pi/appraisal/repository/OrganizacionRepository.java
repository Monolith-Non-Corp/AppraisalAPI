package com.pi.appraisal.repository;

import com.pi.appraisal.entity.Organizacion;
import com.pi.appraisal.entity.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

public interface OrganizacionRepository extends Repository<Organizacion, Integer> {
    Organizacion save(Organizacion organizacion);

    void delete(Organizacion organizacion);

    @Query("SELECT o FROM Organizacion o"
            + " JOIN Usuario u ON u.organizacion = o.id"
            + " WHERE o.id = :organizacion AND u = :usuario")
    Organizacion findByUsuario(
            @Param("organizacion") Integer organizacion,
            @Param("usuario") Usuario usuario
    );
}
