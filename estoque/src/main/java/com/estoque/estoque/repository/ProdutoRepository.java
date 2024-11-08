package com.estoque.estoque.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.estoque.estoque.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    
    @Query("SELECT p FROM Produto p WHERE p.nome LIKE %:termo% OR p.descricao LIKE %:termo%")
    Page<Produto> findByNomeOrDescricaoContaining(@Param("termo") String termo, Pageable pageable);

    @Query("SELECT p FROM Produto p WHERE p.nome LIKE %:termo%")
    List<Produto> findByNomeContaining(@Param("termo") String termo);
}
