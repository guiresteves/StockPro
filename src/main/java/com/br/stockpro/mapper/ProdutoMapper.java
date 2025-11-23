package com.br.stockpro.mapper;

import com.br.stockpro.dtos.produto.ProdutoListDTO;
import com.br.stockpro.dtos.produto.ProdutoRequestDTO;
import com.br.stockpro.dtos.produto.ProdutoResponseDTO;
import com.br.stockpro.model.Produto;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProdutoMapper {

    // Converte de DTO -> Entity
    @Mapping(target = "categoria", ignore = true)
    Produto toEntity(ProdutoRequestDTO dto);

    // Converter Entity -> ResponseDTO
    @Mapping(source = "categoria.id", target = "categoriaId")
    @Mapping(source = "categoria.nome", target = "categoriaNome")
    ProdutoResponseDTO toResponseDTO(Produto produto);

    // Converter Entity -> ListDTO
    List<ProdutoListDTO> toListDTO(List<Produto> produtos);

    // Atualizar um entity existente com dados do DTO (PUT)

    @Mapping(target = "categoria", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(ProdutoRequestDTO dto, @MappingTarget Produto entity);
}
