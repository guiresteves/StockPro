package com.br.stockpro.dtos.produto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.UUID;

@Schema(description = "DTO usado para listagem de produtos")
public record ProdutoListDTO(
        UUID id,
        String nome,
        String descricao,
        String codigoBarras,
        String unidade,
        Integer estoqueAtual,
        Integer estoqueMinimo,
        BigDecimal precoCusto,
        BigDecimal precoVenda,
        Boolean ativo,
        UUID categoriaId,
        String categoriaNome
) {
}