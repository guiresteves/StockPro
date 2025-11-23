package com.br.stockpro.mapper;

import com.br.stockpro.dtos.categoria.CategoriaListDTO;
import com.br.stockpro.dtos.categoria.CategoriaRequestDTO;
import com.br.stockpro.dtos.categoria.CategoriaResponseDTO;
import com.br.stockpro.model.Categoria;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoriaMapper {

    // Converte de DTO -> Entity
    Categoria toEntity(CategoriaRequestDTO dto);

    // Converter Entity -> ResponseDTO
    CategoriaResponseDTO toResponseDTO(Categoria categoria);

    // Converter Entity -> ListDTO
    List<CategoriaListDTO> toListDTO(List<Categoria> categorias);

    // Atualizar um entity existente com dados do DTO (PUT)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(CategoriaRequestDTO dto, @MappingTarget Categoria entity);
}
