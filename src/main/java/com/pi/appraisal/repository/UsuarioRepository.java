package com.pi.appraisal.repository;

import com.pi.appraisal.entity.Usuario;
import com.pi.appraisal.entity.UsuarioRol;
import org.springframework.data.domain.Example;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends Repository<Usuario, Integer> {
    Usuario save(Usuario usuario);

    void delete(Usuario usuario);

    Optional<Usuario> findByUsernameAndPassword(String username, String password);

    Optional<Usuario> findById(Integer id);

    List<Usuario> findAllByUsuarioRolIsNotLike(UsuarioRol usuarioRol);

    boolean exists(Example<Usuario> example);
}
