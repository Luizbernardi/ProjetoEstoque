package com.estoque.estoque.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.estoque.estoque.model.EstoqueProduto;

public interface EstoqueProdutoRepository extends JpaRepository <EstoqueProduto, Long> {
}
