package com.compasso.avaliacao.repository;

import com.compasso.avaliacao.modelo.Associado;
import com.compasso.avaliacao.modelo.CargoPolitico;
import com.compasso.avaliacao.modelo.Ideologia;
import com.compasso.avaliacao.modelo.Partido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssociadoRepository extends JpaRepository<Associado, Long> {

    Page<Associado> findByCargo(CargoPolitico cargo, Pageable Paginacao);
}
