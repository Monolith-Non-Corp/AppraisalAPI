package com.pi.appraisal.repository;

import com.pi.appraisal.entity.Artefacto;
import com.pi.appraisal.entity.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArtefactoRepository extends Repository<Artefacto, Integer> {
    Artefacto save(Artefacto artefacto);

    void delete(Artefacto artefacto);

    @Query("SELECT a FROM Artefacto a"
            + " JOIN Evidencia e ON a.evidencia = e.id"
            + " JOIN Instancia i ON e.instancia = i.id"
            + " JOIN Organizacion o ON i.organizacion = o.id"
            + " WHERE e.id = :evidencia AND o.usuario = :usuario AND a.id = :artefacto")
    Artefacto findByUsuario(
            @Param("evidencia") Integer evidencia,
            @Param("artefacto") Integer artefacto,
            @Param("usuario") Usuario usuario
    );

    @Query("SELECT a FROM Artefacto a"
            + " JOIN Evidencia e ON a.evidencia = e.id"
            + " JOIN Instancia i ON e.instancia = i.id"
            + " JOIN Organizacion o ON i.organizacion = o.id"
            + " WHERE a.id = :artefacto AND o.usuario = :usuario")
    Artefacto findByUsuario(
            @Param("artefacto") Integer artefacto,
            @Param("usuario") Usuario usuario
    );

    @Query("SELECT a FROM Artefacto a "
            + " JOIN Evidencia e ON a.evidencia = e.id "
            + " JOIN Instancia i ON e.instancia = i.id "
            + " JOIN Organizacion o ON i.organizacion = o.id "
            + " WHERE e.id = :evidencia AND o.usuario = :usuario")
    List<Artefacto> findAllByUsuario(
            @Param("evidencia") Integer evidencia,
            @Param("usuario") Usuario usuario
    );
}
