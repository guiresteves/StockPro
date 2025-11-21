package com.br.stockpro.mapper;

import com.br.stockpro.dtos.produto.ProdutoListDTO;
import com.br.stockpro.dtos.produto.ProdutoRequestDTO;
import com.br.stockpro.dtos.produto.ProdutoResponseDTO;
import com.br.stockpro.model.Produto;
import org.mapstruct.*;

public interface ProdutoMapper {

    // Converte de DTO -> Entity
    Produto toProduto(Produto produto);

    // Converter Entity -> ResponseDTO
    ProdutoResponseDTO toResponseDTO(Produto produto);

    // Converter Entity -> ListDTO
    ProdutoListDTO toListDTO(Produto produto);

    // Atualizar um entity existente com dados do DTO (PUT)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(ProdutoRequestDTO dto, @MappingTarget Produto entity);
}
