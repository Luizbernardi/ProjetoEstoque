package com.estoque.estoque.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.estoque.estoque.model.Estoque;
import com.estoque.estoque.model.EstoqueProduto;
import com.estoque.estoque.model.Produto;

public interface EstoqueProdutoRepository extends JpaRepository <EstoqueProduto, Long> {
         Optional<EstoqueProduto> findByEstoqueAndProduto(Estoque estoque, Produto produto);

}
