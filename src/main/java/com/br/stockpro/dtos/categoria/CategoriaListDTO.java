package com.br.stockpro.dtos.categoria;

import java.util.UUID;

public record CategoriaListDTO(
        UUID Id,
        String nome
) {
}
