package com.compasso.avaliacao.dto;

import com.compasso.avaliacao.modelo.CargoPolitico;
import com.compasso.avaliacao.modelo.Sexo;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class AssociadoFormDTO {
    @NotEmpty
    private String nome;
    @NotNull
    private CargoPolitico cargo;
    @NotNull
    private Sexo sexo;
    @NotNull
    private LocalDate nascimento;
}
