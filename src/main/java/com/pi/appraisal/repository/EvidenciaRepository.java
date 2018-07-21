package com.pi.appraisal.repository;

import com.pi.appraisal.entity.Evidencia;
import com.pi.appraisal.entity.Instancia;
import com.pi.appraisal.entity.PracticaEspecifica;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EvidenciaRepository extends Repository<Evidencia, Integer> {
    void deleteInBatch(Iterable<Evidencia> entities);

    void delete(Evidencia evidencia);

    Evidencia findByInstanciaAndPracticaEspecifica(Instancia instancia, PracticaEspecifica practicaEspecifica);

    @Query("SELECT e FROM Evidencia e "
            + " JOIN Instancia i ON e.instancia = i.id "
            + " JOIN Organizacion o ON i.organizacion = o.id "
            + " JOIN Usuario u ON u.organizacion = o.id"
            + " WHERE e.id = :evidencia AND u.id = :usuario")
    Evidencia findByUsuario(
            @Param("evidencia") Integer evidencia,
            @Param("usuario") Integer usuario
    );

    @Query("SELECT e FROM Evidencia e"
            + " JOIN PracticaEspecifica p ON p.id = e.practicaEspecifica"
            + " JOIN MetaEspecifica m ON m.id = p.metaEspecifica"
            + " JOIN AreaProceso a ON a.id = m.areaProceso "
            + " WHERE a.id = :area AND e.instancia = :instancia")
    List<Evidencia> findAllByArea(
            @Param("area") Integer area,
            @Param("instancia") Instancia instancia
    );
}
