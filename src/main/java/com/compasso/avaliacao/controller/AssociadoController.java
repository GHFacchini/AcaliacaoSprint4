package com.compasso.avaliacao.controller;

import com.compasso.avaliacao.dto.*;
import com.compasso.avaliacao.modelo.Associado;
import com.compasso.avaliacao.modelo.CargoPolitico;
import com.compasso.avaliacao.modelo.Partido;
import com.compasso.avaliacao.repository.AssociadoRepository;
import com.compasso.avaliacao.repository.PartidoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/associados")
public class AssociadoController {

    @Autowired
    private AssociadoRepository associadoRepository;

    @Autowired
    private PartidoRepository partidoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @Transactional
    public ResponseEntity<AssociadoDTO> save(@RequestBody @Valid AssociadoFormDTO associadoForm) {
        Associado associado = associadoRepository.save(formParaEntidade(associadoForm));
        return ResponseEntity.status(201).body(entidadeParaDTO(associado));
    }

    @PostMapping("/partidos")
    @Transactional
    public ResponseEntity<AssociadoDTO> adicionarAoPartido(@RequestBody @Valid AdicionarAssociadoAoPartidoFormDTO adicionarForm) {
        Optional<Associado> associadoOptional = associadoRepository.findById(adicionarForm.getIdAssociado());
        if (associadoOptional.isPresent()) {
            Associado associado = associadoOptional.get();
            Optional<Partido> partidoOptional = partidoRepository.findById(adicionarForm.getIdPartido());
            if (partidoOptional.isPresent()) {
                Partido partido = partidoOptional.get();


                associado.setPartido(partido);
                associadoRepository.save(associado);

                partido.getAssociados().add(associado);
                partidoRepository.save(partido);
                return ResponseEntity.ok(entidadeParaDTO(associado));
            }
        }

        return ResponseEntity.notFound().build();
    }


    @DeleteMapping("/{id1}/partidos/{id2}")
    @Transactional
    public ResponseEntity<?> removerDoPartido(@PathVariable Long associadoId, @PathVariable Long partidoId) {
        Optional<Associado> associadoOptional = associadoRepository.findById(associadoId);
        if (associadoOptional.isPresent()) {
            Associado associado = associadoOptional.get();
            Optional<Partido> partidoOptional = partidoRepository.findById(partidoId);
            if (partidoOptional.isPresent()) {
                Partido partido = partidoOptional.get();

                associado.setPartido(null);
                associadoRepository.save(associado);

                partido.getAssociados().remove(associado);
                partidoRepository.save(partido);

                return ResponseEntity.ok().build();
            }
        }
        return ResponseEntity.ok().build();
    }


    @GetMapping
    public ResponseEntity<Page<AssociadoDTO>> lista(@PageableDefault(size = 5, page = 0, direction = Sort.Direction.ASC, sort = "nome") Pageable paginacao,
                                                    @RequestParam(value = "cargo", required = false) CargoPolitico cargo) {
        Page<Associado> pageable;
        if (cargo == null) {
            pageable = this.associadoRepository.findAll(paginacao);
        } else {
            pageable = this.associadoRepository.findByCargo(cargo, paginacao);
        }

        List<AssociadoDTO> associados = pageable.getContent()
                .stream()
                .map(this::entidadeParaDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new PageImpl(associados, paginacao, pageable.getTotalElements()));
    }


    @GetMapping("/{id}")
    public ResponseEntity<AssociadoDTO> buscar(@PathVariable Long id) {
        Optional<Associado> associadoOptional = associadoRepository.findById(id);
        if (associadoOptional.isPresent()) {
            Associado associado = associadoOptional.get();
            System.out.println(associado);
            System.out.println(entidadeParaDTO(associado));
            return ResponseEntity.ok(entidadeParaDTO(associado));
        }

        return ResponseEntity.notFound().build();
    }


    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<AssociadoDTO> atualizar(@PathVariable Long id, @RequestBody @Valid AssociadoFormDTO associadoForm) {
        Optional<Associado> associadoOptional = associadoRepository.findById(id);
        System.out.println("Associado optional: " + associadoOptional);
        if (associadoOptional.isPresent()) {
            Associado associado = associadoOptional.get();
            associado.setNome(associadoForm.getNome());
            associado.setCargo(associadoForm.getCargo());
            associado.setSexo(associadoForm.getSexo());
            associado.setNascimento(associadoForm.getNascimento());
            associadoRepository.save(associado);
            return ResponseEntity.ok(entidadeParaDTO(associado));
        }

        return ResponseEntity.notFound().build();
    }


    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> remover(@PathVariable Long id) {
        Optional<Associado> associadoOptional = associadoRepository.findById(id);
        if (associadoOptional.isPresent()) {
            associadoRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }


    private AssociadoDTO entidadeParaDTO(Associado associado) {
        return modelMapper.map(associado, AssociadoDTO.class);
    }

    private Associado formParaEntidade(AssociadoFormDTO associadoForm) {
        return modelMapper.map(associadoForm, Associado.class);
    }


}
