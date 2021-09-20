package com.compasso.avaliacao.modelo;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class Partido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String sigla;
    @Enumerated(EnumType.STRING)
    private Ideologia ideologia;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate criacao;
    @OneToMany(mappedBy = "partido", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Associado> associados;


}
