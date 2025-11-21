package com.br.stockpro.dtos.produto;

import java.util.UUID;

public record ProdutoListDTO(
        UUID id,
        String nome,
        String codigoBarras,
        Integer estoqueAtual,
        Integer estoqueMinimo,
        Boolean ativo
) {
}