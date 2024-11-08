package com.estoque.estoque.controllers.estoque;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.estoque.estoque.exception.ResourceNotFoundException;
import com.estoque.estoque.model.Produto;
import com.estoque.estoque.repository.ProdutoRepository;

@RestController
@RequestMapping("/api/v1/produto")
@CrossOrigin(origins = "http://localhost:4200")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

   //listar todos os produtos paginados
   @GetMapping("/produtos")
    public ResponseEntity<Page<Produto>> getAllProdutos(@RequestParam int page, @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Produto> produtos = produtoRepository.findAll(pageable);
        return ResponseEntity.ok(produtos);
    }

      //Criando Produto Rest APi
    @PostMapping("/produtos")
    public Produto createProduto(@RequestBody Produto produto) {
        return produtoRepository.save(produto);
    }

    //get produto por id
    @GetMapping("/produtos/{id}")
    public ResponseEntity<Produto> getProdutoById(@PathVariable Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado" + id));
                return ResponseEntity.ok(produto);
    }

     // update produto
    @PatchMapping("produtos/{id}")
    public ResponseEntity<Produto> updateProduto(@PathVariable Long id, @RequestBody Produto produtoDetalhes) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado" + id));
        produto.setNome(produtoDetalhes.getNome());
        produto.setDescricao(produtoDetalhes.getDescricao());
        produto.setPreco(produtoDetalhes.getPreco());
        Produto updatedProduto = produtoRepository.save(produto);
        return ResponseEntity.ok(updatedProduto);
    }

     // delete produto
    @DeleteMapping("/produtos/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteProduto(@PathVariable Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado" + id));
        produtoRepository.delete(produto);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

     // Buscar produtos por nome ou descrição
     @GetMapping("/produtos/search")
    public ResponseEntity<Page<Produto>> findAllMatches(@RequestParam String termo,
                                                        @RequestParam int page,
                                                        @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Produto> produtos = produtoRepository.findByNomeOrDescricaoContaining(termo, pageable);
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/produtos/search-cadastro")
    public ResponseEntity<List<Produto>> searchProduto(@RequestParam String termo) {
        List<Produto> produtos = produtoRepository.findByNomeContaining(termo);
        return ResponseEntity.ok(produtos);
    }

}
