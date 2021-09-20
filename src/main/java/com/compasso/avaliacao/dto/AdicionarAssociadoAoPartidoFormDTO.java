package com.compasso.avaliacao.dto;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AdicionarAssociadoAoPartidoFormDTO {
    @NotNull
    private Long idAssociado;
    @NotNull
    private Long idPartido;
}
