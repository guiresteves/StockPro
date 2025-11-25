package com.br.stockpro.mapper;

import com.br.stockpro.dtos.movimentacao.MovimentacaoListDTO;
import com.br.stockpro.dtos.movimentacao.MovimentacaoRequestDTO;
import com.br.stockpro.dtos.movimentacao.MovimentacaoResponseDTO;
import com.br.stockpro.model.Movimentacao;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MovimentacaoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "produto", ignore = true)
    @Mapping(target = "estoqueAnterior", ignore = true)
    @Mapping(target = "estoquePosterior", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Movimentacao toEntity(MovimentacaoRequestDTO dto);

    @Mapping(source = "produto.id", target = "produtoId")
    @Mapping(source = "produto.nome", target = "produtoNome")
    MovimentacaoResponseDTO toResponseDTO(Movimentacao movimentacao);

    @Mapping(source = "produto.nome", target = "produtoNome")
    MovimentacaoListDTO toListDTO(Movimentacao movimentacao);

    List<MovimentacaoListDTO> toListDTO(List<Movimentacao> movimentacoes);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "produto", ignore = true)
    @Mapping(target = "estoqueAnterior", ignore = true)
    @Mapping(target = "estoquePosterior", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDTO(MovimentacaoRequestDTO dto, @MappingTarget Movimentacao entity);
}
