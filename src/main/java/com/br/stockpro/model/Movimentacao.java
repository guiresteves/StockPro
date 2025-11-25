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
@Table(name = "movimentacao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Movimentacao {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoMovimentacao tipoMovimentacao;

    @Column(nullable = false)
    private Integer quantidade;

    @Column(nullable = false)
    private Integer estoqueAtual;

    @Column(nullable = false)
    private Integer estoquePosterior;

    private String observacao;

    @Column(nullable = false)
    private LocalDateTime dataMovimentacao = LocalDateTime.now();

    private Instant createdAt;
    private Instant updatedAt;
}
