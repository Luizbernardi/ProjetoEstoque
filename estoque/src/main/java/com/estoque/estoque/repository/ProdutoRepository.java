package com.estoque.estoque.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.estoque.estoque.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    Produto findByNome(String nome);
}
