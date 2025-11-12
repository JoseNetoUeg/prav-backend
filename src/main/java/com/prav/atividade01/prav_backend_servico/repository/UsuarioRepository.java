package com.prav.atividade01.prav_backend_servico.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prav.atividade01.prav_backend_servico.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
    boolean existsByEmail(String email);
}
