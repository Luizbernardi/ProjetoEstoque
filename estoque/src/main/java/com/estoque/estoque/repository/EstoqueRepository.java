package com.estoque.estoque.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.estoque.estoque.model.Estoque;

public interface EstoqueRepository extends JpaRepository<Estoque, Long> {
    
}
