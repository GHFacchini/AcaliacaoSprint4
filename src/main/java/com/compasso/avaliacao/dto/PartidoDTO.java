package com.compasso.avaliacao.dto;

import com.compasso.avaliacao.modelo.Associado;
import com.compasso.avaliacao.modelo.Ideologia;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
public class PartidoDTO {

    private Long id;
    private String nome;
    private String sigla;
    private Ideologia ideologia;
    private LocalDate criacao;
    private List<Associado> associados;
}
