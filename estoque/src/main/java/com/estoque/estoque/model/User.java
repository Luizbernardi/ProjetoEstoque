package com.estoque.estoque.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String nome;

    @NotNull
    private String email;
    
    @NotNull
    private String senha;

    
//    @OneToMany(mappedBy = "user")
//    private List<Produto> produtos;

//    @OneToMany(mappedBy = "user")
//    private List<Estoque> estoques;

//    @OneToMany(mappedBy = "user")
//    private List<EstoqueProduto> estoqueProdutos;
    
    
}
