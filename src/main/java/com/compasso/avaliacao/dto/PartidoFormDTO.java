package com.compasso.avaliacao.dto;

import com.compasso.avaliacao.modelo.Ideologia;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
@Data
public class PartidoFormDTO {
    @NotEmpty
    private String nome;
    @NotEmpty
    private String sigla;
    @NotNull
    private Ideologia ideologia;
    @NotNull
    private LocalDate criacao;
}
