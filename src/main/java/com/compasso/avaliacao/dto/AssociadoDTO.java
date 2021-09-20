package com.compasso.avaliacao.dto;

import com.compasso.avaliacao.modelo.CargoPolitico;
import com.compasso.avaliacao.modelo.Partido;
import com.compasso.avaliacao.modelo.Sexo;
import lombok.Data;


import java.util.Date;
@Data
public class AssociadoDTO {

    private Long id;
    private String nome;
    private CargoPolitico cargo;
    private Date AnoNascimento;
    private Sexo sexo;
    private Partido partido;
}
