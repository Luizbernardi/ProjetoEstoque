package com.estoque.estoque.model;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String nome;

    @NotNull
    private String descricao;

    @NotNull
    @DecimalMin(value = "0.01", message = "O preço não pode ser menor que 0,01")
    @DecimalMax(value = "1000000.00", message = "O preço não pode ser maior que um Milhão")
    @NumberFormat(style = Style.CURRENCY, pattern = "¤#,##0.00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "¤#,##0.00")
    private BigDecimal preco;

    @JsonIgnore
    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL , orphanRemoval = true)
    private List<EstoqueProduto> estoqueProdutos;

    @Column(name = "data_entrada", columnDefinition = "TIMESTAMP")
    private LocalDateTime dataEntrada = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);

     // Campo para armazenar o preço formatado
     private transient String precoFormatado;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}