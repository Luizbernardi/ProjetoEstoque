package com.estoque.estoque.model;


import java.time.LocalDateTime;
import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Estoque {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "estoque")
    private List<EstoqueProduto> estoqueProdutos;

    private String nome;

    private LocalDateTime dataEntrada = LocalDateTime.now();

    private LocalDateTime dataSaida;
}


