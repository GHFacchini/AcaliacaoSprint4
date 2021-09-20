package com.compasso.avaliacao.repository;

import com.compasso.avaliacao.modelo.Ideologia;
import com.compasso.avaliacao.modelo.Partido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartidoRepository extends JpaRepository<Partido, Long> {

    Page<Partido> findByIdeologia(Ideologia ideologia, Pageable Paginacao);
}
