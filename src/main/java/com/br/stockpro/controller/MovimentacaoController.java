package com.br.stockpro.controller;

import com.br.stockpro.dtos.movimentacao.MovimentacaoListDTO;
import com.br.stockpro.dtos.movimentacao.MovimentacaoRequestDTO;
import com.br.stockpro.dtos.movimentacao.MovimentacaoResponseDTO;
import com.br.stockpro.enums.TipoMovimentacao;
import com.br.stockpro.service.MovimentacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/movimentacoes")
@RequiredArgsConstructor
@Tag(name = "Movimentações", description = "Gerenciamento de movimentações de entrada, saída e ajuste de produtos")
public class MovimentacaoController {

    private final MovimentacaoService movimentacaoService;

    @PostMapping
    @Operation(
            summary = "Registrar uma nova movimentação",
            description = "Cria uma movimentação de entrada, saída ou ajuste para um produto.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Movimentação registrada com sucesso",
                            content = @Content(schema = @Schema(implementation = MovimentacaoResponseDTO.class))
                    )
            }
    )
    public ResponseEntity<MovimentacaoResponseDTO> create(
            @Valid @RequestBody MovimentacaoRequestDTO dto
    ) {
        return ResponseEntity.ok(movimentacaoService.create(dto));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar movimentação por ID",
            description = "Retorna os dados de uma movimentação específica.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Movimentação encontrada"),
                    @ApiResponse(responseCode = "404", description = "Movimentação não encontrada")
            }
    )
    public ResponseEntity<MovimentacaoResponseDTO> findById(
            @Parameter(description = "ID da movimentação") @PathVariable UUID id
    ) {
        return ResponseEntity.ok(movimentacaoService.findById(id));
    }

    @GetMapping
    @Operation(summary = "Listar todas as movimentações",
            description = "Retorna uma lista de todas as movimentações registradas no sistema.")
    public ResponseEntity<List<MovimentacaoListDTO>> findAll() {
        return ResponseEntity.ok(movimentacaoService.findAll());
    }

    @GetMapping("/produto/{produtoId}")
    @Operation(summary = "Listar movimentações por produto",
            description = "Retorna todas as movimentações associadas a um produto.")
    public ResponseEntity<List<MovimentacaoListDTO>> findByProduto(
            @Parameter(description = "ID do produto") @PathVariable UUID produtoId
    ) {
        return ResponseEntity.ok(movimentacaoService.findByProduto(produtoId));
    }

    @GetMapping("/tipo/{tipo}")
    @Operation(summary = "Listar movimentações por tipo",
            description = "Filtra movimentações por tipo: ENTRADA, SAIDA ou AJUSTE.")
    public ResponseEntity<List<MovimentacaoListDTO>> findByTipo(
            @Parameter(description = "Tipo da movimentação") @PathVariable TipoMovimentacao tipo
    ) {
        return ResponseEntity.ok(movimentacaoService.findByTipoMovimentacao(tipo));
    }

    @GetMapping("/referencia/{referenciaId}")
    @Operation(summary = "Listar movimentações por referência",
            description = "Retorna todas as movimentações relacionadas a uma referência específica (Pedido, Ajuste, etc).")
    public ResponseEntity<List<MovimentacaoListDTO>> findByReferencia(
            @Parameter(description = "ID da referência vinculada") @PathVariable UUID referenciaId
    ) {
        return ResponseEntity.ok(movimentacaoService.findByReferencia(referenciaId));
    }

    @GetMapping("/loja/{lojaId}")
    @Operation(summary = "Listar movimentações por loja",
            description = "Retorna todas as movimentações registradas em uma loja específica.")
    public ResponseEntity<List<MovimentacaoListDTO>> findByLoja(
            @Parameter(description = "ID da loja") @PathVariable UUID lojaId
    ) {
        return ResponseEntity.ok(movimentacaoService.findByLojaId(lojaId));
    }

    @GetMapping("/periodo")
    @Operation(summary = "Listar movimentações por período",
            description = "Filtra movimentações entre datas específicas (início e fim).")
    public ResponseEntity<List<MovimentacaoListDTO>> findByPeriodo(
            @Parameter(description = "Data inicial no formato ISO") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant inicio,
            @Parameter(description = "Data final no formato ISO") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant fim
    ) {
        return ResponseEntity.ok(movimentacaoService.findByPeriodo(inicio, fim));
    }

    @GetMapping("/produto/periodo")
    @Operation(summary = "Listar movimentações por produto e período",
            description = "Filtra movimentações de um produto específico dentro de um intervalo de datas.")
    public ResponseEntity<List<MovimentacaoListDTO>> findByProdutoPeriodo(
            @Parameter(description = "ID do produto") @RequestParam UUID produtoId,
            @Parameter(description = "Data inicial no formato ISO") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant inicio,
            @Parameter(description = "Data final no formato ISO") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant fim
    ) {
        return ResponseEntity.ok(movimentacaoService.findByProdutoPeriodo(produtoId, inicio, fim));
    }

    @GetMapping("/loja/periodo")
    @Operation(summary = "Listar movimentações por loja e período",
            description = "Filtra movimentações de uma loja específica dentro de um intervalo de datas.")
    public ResponseEntity<List<MovimentacaoListDTO>> findByLojaPeriodo(
            @Parameter(description = "ID da loja") @RequestParam UUID lojaId,
            @Parameter(description = "Data inicial no formato ISO") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant inicio,
            @Parameter(description = "Data final no formato ISO") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant fim
    ) {
        return ResponseEntity.ok(movimentacaoService.findByLojaPeriodo(lojaId, inicio, fim));
    }

    @GetMapping("/tipo/periodo")
    @Operation(summary = "Listar movimentações por tipo e período",
            description = "Filtra movimentações de um tipo específico dentro de um intervalo de datas.")
    public ResponseEntity<List<MovimentacaoListDTO>> findByTipoPeriodo(
            @Parameter(description = "Tipo da movimentação") @RequestParam TipoMovimentacao tipo,
            @Parameter(description = "Data inicial no formato ISO") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant inicio,
            @Parameter(description = "Data final no formato ISO") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant fim
    ) {
        return ResponseEntity.ok(movimentacaoService.findByTipoPeriodo(tipo, inicio, fim));
    }

    @GetMapping("/produto/tipo/periodo")
    @Operation(summary = "Listar movimentações por produto, tipo e período",
            description = "Filtra movimentações de um produto específico e tipo dentro de um intervalo de datas.")
    public ResponseEntity<List<MovimentacaoListDTO>> findByProdutoTipoPeriodo(
            @Parameter(description = "ID do produto") @RequestParam UUID produtoId,
            @Parameter(description = "Tipo da movimentação") @RequestParam TipoMovimentacao tipo,
            @Parameter(description = "Data inicial no formato ISO") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant inicio,
            @Parameter(description = "Data final no formato ISO") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant fim
    ) {
        return ResponseEntity.ok(movimentacaoService.findByProdutoTipoPeriodo(produtoId, tipo, inicio, fim));
    }

    @GetMapping("/loja/tipo/periodo")
    @Operation(summary = "Listar movimentações por loja, tipo e período",
            description = "Filtra movimentações de uma loja específica e tipo dentro de um intervalo de datas.")
    public ResponseEntity<List<MovimentacaoListDTO>> findByLojaTipoPeriodo(
            @Parameter(description = "ID da loja") @RequestParam UUID lojaId,
            @Parameter(description = "Tipo da movimentação") @RequestParam TipoMovimentacao tipo,
            @Parameter(description = "Data inicial no formato ISO") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant inicio,
            @Parameter(description = "Data final no formato ISO") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant fim
    ) {
        return ResponseEntity.ok(movimentacaoService.findByLojaTipoPeriodo(lojaId, tipo, inicio, fim));
    }

    @GetMapping("/produto/loja/periodo")
    @Operation(summary = "Listar movimentações por produto, loja e período",
            description = "Filtra movimentações de um produto específico em uma loja dentro de um intervalo de datas.")
    public ResponseEntity<List<MovimentacaoListDTO>> findByProdutoLojaPeriodo(
            @Parameter(description = "ID do produto") @RequestParam UUID produtoId,
            @Parameter(description = "ID da loja") @RequestParam UUID lojaId,
            @Parameter(description = "Data inicial no formato ISO") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant inicio,
            @Parameter(description = "Data final no formato ISO") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant fim
    ) {
        return ResponseEntity.ok(movimentacaoService.findByProdutoLojaPeriodo(produtoId, lojaId, inicio, fim));
    }

    @GetMapping("/historico/produto/{produtoId}")
    @Operation(summary = "Histórico de movimentações de um produto",
            description = "Retorna todas as movimentações de um produto ordenadas da mais recente para a mais antiga.")
    public ResponseEntity<List<MovimentacaoListDTO>> findHistoricoProduto(
            @Parameter(description = "ID do produto") @PathVariable UUID produtoId
    ) {
        return ResponseEntity.ok(movimentacaoService.findHistoricoProduto(produtoId));
    }

    @GetMapping("/ordem/desc")
    @Operation(summary = "Listar todas movimentações por data decrescente",
            description = "Retorna todas as movimentações registradas ordenadas da mais recente para a mais antiga.")
    public ResponseEntity<List<MovimentacaoListDTO>> findAllOrderByDataDesc() {
        return ResponseEntity.ok(movimentacaoService.findAllOrderByDataDesc());
    }
}
