package com.pi.appraisal.repository;

import com.pi.appraisal.entity.AreaProceso;
import com.pi.appraisal.entity.Evidencia;
import com.pi.appraisal.entity.Instancia;
import com.pi.appraisal.entity.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EvidenciaRepository extends Repository<Evidencia, Integer> {
    Evidencia save(Evidencia evidencia);

    void delete(Evidencia evidencia);

    @Query("SELECT e FROM Evidencia e "
            + " JOIN Instancia i ON e.instancia = i.id "
            + " JOIN Organizacion o ON i.organizacion = o.id "
            + " WHERE e.id = :evidencia AND o.usuario = :usuario")
    Evidencia findByUsuario(
            @Param("evidencia") Integer evidencia,
            @Param("usuario") Usuario usuario
    );

    @Query("SELECT e FROM Evidencia e"
            + " JOIN PracticaEspecifica p ON p.id = e.practicaEspecifica"
            + " JOIN MetaEspecifica m ON m.id = p.metaEspecifica"
            + " JOIN AreaProceso a ON a.id = m.areaProceso "
            + " WHERE a = :area AND e.instancia =  :instancia")
    List<Evidencia> findAllByArea(
            @Param("area") AreaProceso area,
            @Param("instancia") Instancia instancia
    );
}
