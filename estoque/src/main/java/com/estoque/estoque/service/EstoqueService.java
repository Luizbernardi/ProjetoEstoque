package com.estoque.estoque.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.estoque.estoque.model.Estoque;
import com.estoque.estoque.model.EstoqueProduto;
import com.estoque.estoque.model.Produto;
import com.estoque.estoque.repository.EstoqueProdutoRepository;
import com.estoque.estoque.repository.EstoqueRepository;
import com.estoque.estoque.repository.ProdutoRepository;

@Service
public class EstoqueService {

    @Autowired
    private EstoqueRepository estoqueRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private EstoqueProdutoRepository estoqueProdutoRepository;    

     public List<EstoqueProduto> verificarEstoqueTotal() {
        return estoqueProdutoRepository.findAll();
    }

    public double calcularPrecoTotalProdutoNoEstoque(Long estoqueId, Long produtoId) {
        Optional<Estoque> estoqueOptional = estoqueRepository.findById(estoqueId);
        Optional<Produto> produtoOptional = produtoRepository.findById(produtoId);

        if (estoqueOptional.isPresent() && produtoOptional.isPresent()) {
            Estoque estoque = estoqueOptional.get();
            Produto produto = produtoOptional.get();

            Optional<EstoqueProduto> estoqueProdutoOptional = estoqueProdutoRepository.findByEstoqueAndProduto(estoque, produto);

            if (estoqueProdutoOptional.isPresent()) {
                EstoqueProduto estoqueProduto = estoqueProdutoOptional.get();
                return estoqueProduto.getQuantidade() * produto.getPreco();
            }
        }
        return 0.0;
    }

    public double calcularPrecoTotalEstoque(Long estoqueId) {
        Optional<Estoque> estoqueOptional = estoqueRepository.findById(estoqueId);

        if (estoqueOptional.isPresent()) {
            Estoque estoque = estoqueOptional.get();
            List<EstoqueProduto> estoqueProdutos = estoqueProdutoRepository.findByEstoque(estoque);

            return estoqueProdutos.stream()
                    .mapToDouble(ep -> ep.getQuantidade() * ep.getProduto().getPreco())
                    .sum();
        }
        return 0.0;
    }

    public String cadastrarProdutoEmEstoque(Long estoqueId, Long produtoId, int quantidade) {
        Optional<Estoque> estoqueOptional = estoqueRepository.findById(estoqueId);
        Optional<Produto> produtoOptional = produtoRepository.findById(produtoId);

        if (estoqueOptional.isPresent() && produtoOptional.isPresent()) {
            Estoque estoque = estoqueOptional.get();
            Produto produto = produtoOptional.get();

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
