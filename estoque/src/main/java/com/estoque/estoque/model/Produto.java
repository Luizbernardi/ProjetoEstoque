package com.estoque.estoque.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @DecimalMin(value = "0.01", message = "O preço não pode ser menor que 0,01")
    @DecimalMax(value = "1000000.00", message = "O preço não pode ser maior que um Milhão")
    private Double preco;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL , orphanRemoval = true)
    private List<EstoqueProduto> estoqueProdutos = new ArrayList<>();

    private LocalDateTime dataEntrada = LocalDateTime.now();

     // Campo para armazenar o preço formatado
     private transient String precoFormatado;


    public void addEstoqueProduto(EstoqueProduto estoqueProduto) {
        estoqueProdutos.add(estoqueProduto);
        estoqueProduto.setProduto(this);
    }

    public void removeEstoqueProduto(EstoqueProduto estoqueProduto) {
        estoqueProdutos.remove(estoqueProduto);
        estoqueProduto.setProduto(null);
    }
}