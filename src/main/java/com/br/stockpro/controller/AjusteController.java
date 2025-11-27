package com.br.stockpro.controller;

import com.br.stockpro.dtos.movimentacao.AjusteRequestDTO;
import com.br.stockpro.dtos.movimentacao.MovimentacaoResponseDTO;
import com.br.stockpro.service.AjusteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ajustes")
@RequiredArgsConstructor
@Tag(
        name = "Ajustes de Estoque",
        description = "Operações responsáveis por ajustes manuais no estoque"
)
public class AjusteController {

    private final AjusteService ajusteService;

    @PostMapping
    @Operation(
            summary = "Realizar ajuste de estoque",
            description = "Permite ajustar manualmente a quantidade de um produto no estoque (positiva ou negativa).",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Ajuste realizado com sucesso",
                            content = @Content(schema = @Schema(implementation = MovimentacaoResponseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Dados inválidos enviados na requisição"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Produto não encontrado"
                    )
            }
    )
    public ResponseEntity<MovimentacaoResponseDTO> ajustarEstoque(
            @Valid
            @RequestBody
            @Parameter(description = "Informações para ajuste do estoque")
            AjusteRequestDTO dto
    ) {
        return ResponseEntity.ok(ajusteService.ajustarEstoque(dto));
    }
}
