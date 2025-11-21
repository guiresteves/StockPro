package com.br.stockpro.dtos.produto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ProdutoRequestDTO(
        @NotBlank
        String nome,

        String descricao,

        @NotBlank(message = "O código de barras é obrigatório")
        String codigoBarras,

        @NotBlank(message = "A unidade é obrigatória")
        String unidade,

        @NotNull(message = "Informe se o produto possui validade (true/false)")
        Boolean temValidade,

        LocalDate dataValidade,

        @NotNull @Min(value = 0, message = "O estoque atual não pode ser negativo")
        Integer estoqueAtual,

        @NotNull @Min(value = 0, message = "O estoque mínimo não pode ser negativo")
        Integer estoqueMinimo,

        @NotNull @DecimalMin(value = "0.00", message = "Preço de custo inválido")
        BigDecimal precoCusto,

        @NotNull @DecimalMin(value = "0.00", message = "Preço de venda inválido")
        BigDecimal precoVenda
) {
}