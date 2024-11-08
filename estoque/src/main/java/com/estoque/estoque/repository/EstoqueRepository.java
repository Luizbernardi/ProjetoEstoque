package com.estoque.estoque.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.estoque.estoque.model.Estoque;

public interface EstoqueRepository extends JpaRepository<Estoque, Long> {

    @Query("SELECT e FROM Estoque e WHERE e.nome LIKE %:termo%")
    List<Estoque> findByNomeContaining(@Param("termo") String termo);
}
