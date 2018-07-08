package com.pi.appraisal.repository;

import com.pi.appraisal.entity.Instancia;
import com.pi.appraisal.entity.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

public interface InstanciaRepository extends Repository<Instancia, Integer> {
    Instancia save(Instancia instancia);

    void delete(Instancia instancia);

    @Query("SELECT i FROM Instancia i"
            + " JOIN Organizacion o ON i.organizacion = o.id"
            + " WHERE i.id = :instancia AND o.usuario = :usuario ")
    Instancia findByUsuario(
            @Param("instancia") Integer instancia,
            @Param("usuario") Usuario usuario
    );
}
