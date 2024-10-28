package com.estoque.estoque.service;

import com.estoque.estoque.model.Estoque;
import com.estoque.estoque.model.EstoqueProduto;
import com.estoque.estoque.model.Produto;
import com.estoque.estoque.repository.EstoqueProdutoRepository;
import com.estoque.estoque.repository.EstoqueRepository;
import com.estoque.estoque.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EstoqueProdutoService {

    @Autowired
    private EstoqueRepository estoqueRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private EstoqueProdutoRepository estoqueProdutoRepository;

    public String cadastrarProdutoEmEstoque(Long estoqueId, Long produtoId, int quantidade) {
        Optional<Estoque> estoqueOptional = estoqueRepository.findById(estoqueId);
        Optional<Produto> produtoOptional = produtoRepository.findById(produtoId);

        if (estoqueOptional.isPresent() && produtoOptional.isPresent()) {
            Estoque estoque = estoqueOptional.get();
            Produto produto = produtoOptional.get();

            // Verifica se já existe um registro para o mesmo produto e estoque
            Optional<EstoqueProduto> estoqueProdutoOptional = estoqueProdutoRepository.findByEstoqueAndProduto(estoque, produto);

            if (estoqueProdutoOptional.isPresent()) {
                // Atualiza a quantidade existente
                EstoqueProduto estoqueProduto = estoqueProdutoOptional.get();
                estoqueProduto.setQuantidade(estoqueProduto.getQuantidade() + quantidade);
                estoqueProdutoRepository.save(estoqueProduto);
                return "Quantidade do produto atualizada no estoque com sucesso!";
            } else {
                // Cria um novo registro
                EstoqueProduto estoqueProduto = new EstoqueProduto(null, estoque, produto, quantidade);
                estoqueProdutoRepository.save(estoqueProduto);
                return "Produto cadastrado no estoque com sucesso!";
            }
        } else {
            return "Estoque ou Produto não encontrado.";
        }
    }
}