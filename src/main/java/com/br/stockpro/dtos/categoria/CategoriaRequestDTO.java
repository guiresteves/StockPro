package com.br.stockpro.dtos.categoria;

import jakarta.validation.constraints.NotBlank;

public record CategoriaRequestDTO(
        @NotBlank
        String nome
) {
}
