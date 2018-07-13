package com.pi.appraisal.repository;

import com.pi.appraisal.entity.Instancia;
import com.pi.appraisal.entity.Organizacion;
import com.pi.appraisal.entity.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface InstanciaRepository extends Repository<Instancia, Integer> {
    Instancia save(Instancia instancia);

    void delete(Instancia instancia);

    Optional<Instancia> findById(Integer id);

    List<Instancia> findAllByOrganizacion(Organizacion organizacion);

    @Query("SELECT i FROM Instancia i"
            + " JOIN Organizacion o ON i.organizacion = o.id"
            + " JOIN Usuario u ON u.organizacion = o.id"
            + " WHERE i.id = :instancia AND u.id = :usuario ")
    Instancia findByUsuario(
            @Param("instancia") Integer instancia,
            @Param("usuario") Integer usuario
    );
}
