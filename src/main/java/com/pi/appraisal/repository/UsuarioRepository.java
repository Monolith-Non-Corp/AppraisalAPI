package com.pi.appraisal.repository;

import java.util.Optional;

import org.springframework.data.repository.Repository;

import com.pi.appraisal.entity.Usuario;
import com.pi.appraisal.entity.UsuarioRol;

public interface UsuarioRepository extends Repository<Usuario, Integer> {
	Usuario save(Usuario usuario);
	void delete(Usuario usuario);	

	Optional<Usuario> findByUsernameAndPassword(String username, String password);
	Optional<UsuarioRol> findUsuarioRolById(Integer id);
}
