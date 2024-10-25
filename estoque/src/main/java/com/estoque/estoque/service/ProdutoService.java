package com.estoque.estoque.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.estoque.estoque.model.Produto;
import com.estoque.estoque.repository.ProdutoRepository;

@Service
public class ProdutoService {
    
    @Autowired
    private ProdutoRepository produtoRepository;

    public Produto obterDetalhesProduto(Long id) {
        return produtoRepository.findById(id).orElse(null);
    }

    public Produto atualizarProduto(Produto produto) {
        Produto produtoExistente = produtoRepository.findById(produto.getId()).orElse(null);
        if (produtoExistente != null) {
            produtoExistente.setNome(produto.getNome());
            produtoExistente.setDescricao(produto.getDescricao());
            produtoExistente.setPreco(produto.getPreco());
            return produtoRepository.save(produtoExistente);
        } else {
            return null;
        }
    }

    public Double calcularValorProduto(Long id, Integer quantidade) {
        Produto produto = produtoRepository.findById(id).orElse(null);
        if (produto != null) {
            return produto.getPreco() * quantidade;
        }
        else {
            return null;
        }
    }

     public LocalDateTime registroEntradaProduto(Long id) {
        Optional<Produto> produtoOptional = produtoRepository.findById(id);
        if (produtoOptional.isPresent()) {
            Produto produto = produtoOptional.get();
            produto.setDataEntrada(LocalDateTime.now());
            produtoRepository.save(produto);
            return produto.getDataEntrada();
        } else {
            return null;
        }
    }

    public LocalDateTime registroSaidaProduto(Long id) {
        Optional<Produto> produtoOptional = produtoRepository.findById(id);
        if (produtoOptional.isPresent()) {
            Produto produto = produtoOptional.get();
            produto.setDataSaida(LocalDateTime.now());
            produtoRepository.save(produto);
            return produto.getDataSaida();
        } else {
            return null;
        }
    }



}
