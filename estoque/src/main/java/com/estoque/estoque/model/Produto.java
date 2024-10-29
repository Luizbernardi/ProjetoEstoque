package com.estoque.estoque.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Produto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String nome;

    @NotNull
    private String descricao;

    @NotNull
    private Double preco;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL , orphanRemoval = true)
    private List<EstoqueProduto> estoqueProdutos;

    private LocalDateTime dataEntrada = LocalDateTime.now();
}
