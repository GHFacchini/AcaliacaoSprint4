package com.compasso.avaliacao.controller;

import com.compasso.avaliacao.dto.AssociadoDTO;
import com.compasso.avaliacao.dto.PartidoDTO;
import com.compasso.avaliacao.dto.PartidoFormDTO;
import com.compasso.avaliacao.modelo.Associado;
import com.compasso.avaliacao.modelo.Ideologia;
import com.compasso.avaliacao.modelo.Partido;
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
@RequestMapping("/partidos")
public class PartidoController {

    @Autowired
    private PartidoRepository partidoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @Transactional
    public ResponseEntity<PartidoDTO> save(@RequestBody @Valid PartidoFormDTO partidoForm){
        Partido partido = partidoRepository.save(formParaEntidade(partidoForm));
        return ResponseEntity.status(201).body(entidadeParaDTO(partido));
    }


    @GetMapping
    public ResponseEntity<Page<PartidoDTO>> lista(@PageableDefault(size = 5, page = 0, direction = Sort.Direction.ASC, sort = "nome") Pageable paginacao,
                                  @RequestParam(value = "ideologia", required = false) Ideologia ideologia){
        Page<Partido> pageable;
        if(ideologia == null){
            pageable = this.partidoRepository.findAll(paginacao);
        }else{
            pageable = this.partidoRepository.findByIdeologia(ideologia, paginacao);
        }

        List<PartidoDTO> partidos = pageable.getContent()
                .stream()
                .map(this::entidadeParaDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new PageImpl(partidos, paginacao, pageable.getTotalElements()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PartidoDTO> buscar(@PathVariable Long id){
        Optional<Partido> partidoOptional = partidoRepository.findById(id);
        if(partidoOptional.isPresent()){
            Partido partido = partidoOptional.get();
            return ResponseEntity.ok(entidadeParaDTO(partido));
        }

        return ResponseEntity.notFound().build();
    }





   @GetMapping("/{id}/associados")
   public ResponseEntity<List<AssociadoDTO>> buscarAssociados(@PathVariable Long id){
        Optional<Partido> partidoOptional = partidoRepository.findById(id);
        if(partidoOptional.isPresent()){
            Partido partido = partidoOptional.get();
            List<AssociadoDTO> associados = partido.getAssociados()
                    .stream()
                    .map(this::entidadeParaDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(associados);
        }
       return ResponseEntity.notFound().build();
   }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<PartidoDTO> atualizar (@PathVariable Long id, @RequestBody @Valid PartidoFormDTO partidoForm ){
        Optional<Partido> partidoOptional = partidoRepository.findById(id);
        System.out.println("Partido optional: " + partidoOptional);
        if(partidoOptional.isPresent()){
            Partido partido = partidoOptional.get();
            partido.setNome(partidoForm.getNome());
            partido.setSigla(partidoForm.getSigla());
            partido.setIdeologia(partidoForm.getIdeologia());
            partido.setCriacao(partidoForm.getCriacao());
            partidoRepository.save(partido);
            return ResponseEntity.ok(entidadeParaDTO(partido));
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> remover(@PathVariable Long id){
        Optional<Partido> partidoOptional = partidoRepository.findById(id);
        if(partidoOptional.isPresent()){
            partidoRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }




    private PartidoDTO entidadeParaDTO(Partido partido) {
        return modelMapper.map(partido, PartidoDTO.class);
    }
    private AssociadoDTO entidadeParaDTO(Associado associado) {
        return modelMapper.map(associado, AssociadoDTO.class);
    }

    private Partido formParaEntidade(PartidoFormDTO partidoForm) {
        return modelMapper.map(partidoForm, Partido.class);
    }


}
