package com.compasso.avaliacao.modelo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
public class Associado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @Enumerated(EnumType.STRING)
    private CargoPolitico cargo;
    @Enumerated(EnumType.STRING)
    private Sexo sexo;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate nascimento;

    @ManyToOne
    @JoinColumn(name = "PARTIDO_ID" )
    @JsonBackReference
    private Partido partido;


}
