package com.estoque.estoque.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @OneToMany(mappedBy = "estoque", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EstoqueProduto> estoqueProdutos;

    private String nome;

    @Column(name = "data_entrada", columnDefinition = "TIMESTAMP")
    private LocalDateTime dataEntrada = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);

}