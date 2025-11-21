package com.br.stockpro.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "produtos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    private String nome;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @NotBlank
    @Column(name = "codigo_barras", unique = true, nullable = false)
    private String codigoBarras;

    @NotBlank
    private String unidade;

    @Column(name = "tem_validade", nullable = false)
    private Boolean temValidade = false;

    private LocalDate dataValidade;

    @NotNull
    @Min(0)
    @Column(name = "estoque_atual")
    private Integer estoqueAtual;

    @NotNull
    @Min(0)
    @Column(name = "estoque_minimo")
    private Integer estoqueMinimo;

    @NotNull
    @DecimalMin(value = "0.00", inclusive = true)
    private BigDecimal precoCusto;

    @NotNull
    @DecimalMin(value = "0.00", inclusive = true)
    private BigDecimal precoVenda;

    @Column(nullable = false)
    private Boolean ativo = true;

    private Instant createdAt;
    private Instant updatedAt;


    // Esse método é chamado antes da entidade ser inserida no banco
    @PrePersist
    public void prePersist() {
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
        if (estoqueAtual == null) estoqueAtual = 0;
        if (estoqueMinimo == null) estoqueMinimo = 0;
        if (temValidade == null) temValidade = false;
    }

    // Chamado antes da entidade ser atualizada
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = Instant.now();
    }
}
