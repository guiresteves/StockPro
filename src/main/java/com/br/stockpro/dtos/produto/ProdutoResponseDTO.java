package com.br.stockpro.dtos.produto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Schema(description = "DTO de resposta detalhada de um produto")
public record ProdutoResponseDTO(
        UUID id,
        String nome,
        String descricao,
        String codigoBarras,
        String unidade,
        Boolean temValidade,
        LocalDate dataValidade,
        Integer estoqueAtual,
        Integer estoqueMinimo,
        BigDecimal precoCusto,
        BigDecimal precoVenda,
        Boolean ativo,
        Instant createdAt,
        Instant updatedAt,
        UUID categoriaId,
        String categoriaNome
) {
}
