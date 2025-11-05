package com.prav.atividade01.prav_backend_servico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.prav.atividade01.prav_backend_servico.model.Servico;

@Repository
public interface ServicoRepository extends JpaRepository<Servico, Long> {
}