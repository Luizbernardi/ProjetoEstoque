package com.estoque.estoque.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class EstoqueProdutoRequest {

    @NotNull
    private Long estoqueId;

    @NotNull
    private Long produtoId;

    @Min(value = 1, message = "A quantidade não pode ser menor que 1")
    @Max(value = 100, message = "A quantidade não pode ser maior que 100")
    private Integer quantidade;

    // Getters and setters
    public Long getEstoqueId() {
        return estoqueId;
    }

    public void setEstoqueId(Long estoqueId) {
        this.estoqueId = estoqueId;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}