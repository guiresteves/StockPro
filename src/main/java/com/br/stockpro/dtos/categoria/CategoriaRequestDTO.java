package com.br.stockpro.dtos.categoria;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "DTO de entrada para criação ou atualização de uma categoria")
public record CategoriaRequestDTO(
        @NotBlank
        String nome
) {
}
