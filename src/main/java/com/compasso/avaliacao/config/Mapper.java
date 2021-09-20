package com.compasso.avaliacao.config;

import com.compasso.avaliacao.dto.PartidoDTO;
import com.compasso.avaliacao.modelo.Partido;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class Mapper {

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper;
    }
}
