package com.br.stockpro.mapper;

import com.br.stockpro.dtos.categoria.CategoriaRequestDTO;
import com.br.stockpro.dtos.categoria.CategoriaResponseDTO;
import com.br.stockpro.model.Categoria;
import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

public interface CategoriaMapper {

    // Converte de DTO -> Entity
    Categoria toEntity(CategoriaResponseDTO dto);

    // Converter Entity -> ResponseDTO
    CategoriaResponseDTO toResponseDTO(Categoria categoria);

    // Converter Entity -> ListDTO
    List<CategoriaResponseDTO> toListDTO(List<Categoria> categorias);

    // Atualizar um entity existente com dados do DTO (PUT)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(CategoriaRequestDTO dto, @MappingTarget Categoria entity);
}
