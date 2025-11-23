package com.br.stockpro.repository;

import com.br.stockpro.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProdutoRepository extends JpaRepository<Produto, UUID> {

    Optional<Produto> findByCodigoBarras(String codigoBarras);
    boolean existsByCodigoBarras(String codigoBarras);
    boolean existsByCodigoBarrasAndIdNot(String codigoBarras, UUID id);
    @Query("SELECT p FROM Produto p WHERE p.estoqueAtual <= p.estoqueMinimo")
    List<Produto> findProdutosAbaixoDoEstoqueMinimo();
    List<Produto> findByAtivoTrue();
    List<Produto> findByTemValidadeTrue();
    List<Produto> findByDataValidadeBefore(LocalDate data);
}
