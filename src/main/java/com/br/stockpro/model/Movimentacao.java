package com.br.stockpro.model;


import com.br.stockpro.enums.TipoMovimentacao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "movimentacoes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Movimentacao {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, updatable = false)
    private TipoMovimentacao tipoMovimentacao;

    @Column(nullable = false, updatable = false)
    private Integer quantidade;

    @Column(nullable = false, updatable = false)
    private Integer estoqueAnterior;

    @Column(nullable = false, updatable = false)
    private Integer estoquePosterior;

    @Column
    private Integer diferencaAjuste;

    private String observacao;

    // Campos provisórios até criar módulo de Loja
    @Column(nullable = false)
    private UUID lojaId;

    // Campo provisório até existir o módulo de usuário
    private UUID usuarioId;

    // Auditoria de referência externa
    private UUID referenciaId;
    private String referenciaTipo;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = Instant.now();
    }
}
