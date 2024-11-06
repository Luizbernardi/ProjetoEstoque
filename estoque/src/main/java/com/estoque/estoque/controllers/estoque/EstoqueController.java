package com.estoque.estoque.controllers.estoque;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estoque.estoque.model.Estoque;
import com.estoque.estoque.model.EstoqueProduto;
import com.estoque.estoque.model.EstoqueProdutoRequest;
import com.estoque.estoque.model.Produto;
import com.estoque.estoque.repository.EstoqueProdutoRepository;
import com.estoque.estoque.repository.EstoqueRepository;
import com.estoque.estoque.repository.ProdutoRepository;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/v1/")
@CrossOrigin(origins = "http://localhost:4200")
public class EstoqueController {

    @Autowired
    private EstoqueRepository estoqueRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private EstoqueProdutoRepository estoqueProdutoRepository;

    //listar todos os estoques
    @GetMapping("/estoques")
    public List<Estoque> getAllEstoques() {
        return estoqueRepository.findAll();
    }

    //listar todos os produtoss
    @GetMapping("/produtos")
    public List<Produto> getAllProdutos() {
        return produtoRepository.findAll();
    }

    //listar todos os produtos em estoque
    @GetMapping("/estoque-produtos")
    public List<EstoqueProduto> getAllEstoqueProdutos() {
        return estoqueProdutoRepository.findAll();
    }

    //Criando Produto Rest APi
    @PostMapping("/produtos")
    public Produto createProduto(@RequestBody Produto produto) {
        return produtoRepository.save(produto);
    }

    //Criando Estoque Rest APi
    @PostMapping("/estoques")
    public Estoque createEstoque(@RequestBody Estoque estoque) {
        return estoqueRepository.save(estoque);
    }

    //Criando EstoqueProduto Rest APi
    @PostMapping("/estoque-produtos")
    public ResponseEntity<EstoqueProduto> createEstoqueProduto(@RequestBody EstoqueProdutoRequest request) {
        Estoque estoque = estoqueRepository.findById(request.getEstoqueId())
                .orElseThrow(() -> new RuntimeException("Estoque não encontrado"));
        Produto produto = produtoRepository.findById(request.getProdutoId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        EstoqueProduto estoqueProduto = new EstoqueProduto();
        estoqueProduto.setEstoque(estoque);
        estoqueProduto.setProduto(produto);
        estoqueProduto.setQuantidade(request.getQuantidade());

        EstoqueProduto novoEstoqueProduto = estoqueProdutoRepository.save(estoqueProduto);
        return ResponseEntity.ok(novoEstoqueProduto);
    }

    //get estoque por id
    @GetMapping("/estoques/{id}")
    public ResponseEntity<Estoque> getEstoqueById(@PathVariable Long id) {
        Estoque estoque = estoqueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estoque não encontrado" + id));
                return ResponseEntity.ok(estoque);
    }

    //get produto por id
    @GetMapping("/produtos/{id}")
    public ResponseEntity<Produto> getProdutoById(@PathVariable Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado" + id));
                return ResponseEntity.ok(produto);
    }

    @GetMapping("/estoque-produtos/{id}")
    public ResponseEntity<EstoqueProduto> getEstoqueProdutoId(@PathVariable Long id) {
        EstoqueProduto estoqueProduto = estoqueProdutoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estoque Produto não encontrado" + id));
                return ResponseEntity.ok(estoqueProduto);
    }
    
    // update estoque
    @PatchMapping("/estoques/{id}")
    public ResponseEntity<Estoque> updateEstoque(@PathVariable Long id, @RequestBody Estoque estoqueDetalhes) {
        Estoque estoque = estoqueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estoque não encontrado" + id));
        estoque.setNome(estoqueDetalhes.getNome());
        Estoque updatedEstoque = estoqueRepository.save(estoque);
        return ResponseEntity.ok(updatedEstoque);
    }
    
    // update produto
    @PatchMapping("produtos/{id}")
    public ResponseEntity<Produto> updateProduto(@PathVariable Long id, @RequestBody Produto produtoDetalhes) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado" + id));
        produto.setNome(produtoDetalhes.getNome());
        produto.setDescricao(produtoDetalhes.getDescricao());
        produto.setPreco(produtoDetalhes.getPreco());
        Produto updatedProduto = produtoRepository.save(produto);
        return ResponseEntity.ok(updatedProduto);
    }

    // update estoque produto
    @PatchMapping("estoque-produtos/{id}")
    public ResponseEntity<EstoqueProduto> updateEstoqueProduto(@PathVariable Long id, @RequestBody EstoqueProdutoRequest request) {
        EstoqueProduto estoqueProduto = estoqueProdutoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estoque Produto não encontrado" + id));
        estoqueProduto.setQuantidade(request.getQuantidade());
        EstoqueProduto updatedEstoqueProduto = estoqueProdutoRepository.save(estoqueProduto);
        return ResponseEntity.ok(updatedEstoqueProduto);
    }











}


