package com.estoque.estoque.controllers.estoque;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
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

import com.estoque.estoque.model.Estoque;
import com.estoque.estoque.model.EstoqueProduto;
import com.estoque.estoque.model.EstoqueProdutoRequest;
import com.estoque.estoque.model.Produto;
import com.estoque.estoque.repository.EstoqueProdutoRepository;
import com.estoque.estoque.repository.EstoqueRepository;
import com.estoque.estoque.repository.ProdutoRepository;

@RestController
@RequestMapping("/api/v1/estoque-produto")
@CrossOrigin(origins = "http://localhost:4200")
public class EstoqueProdutoController { 

    @Autowired
    private EstoqueProdutoRepository estoqueProdutoRepository;

    @Autowired
    private EstoqueRepository estoqueRepository;

    @Autowired
    private ProdutoRepository produtoRepository;



     //listar todos os produtos em estoque
   @GetMapping("/estoque-produtos")
    public ResponseEntity<Page<EstoqueProduto>> getAllEstoqueProdutos(@RequestParam int page, @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<EstoqueProduto> estoqueProdutos = estoqueProdutoRepository.findAll(pageable);
        return ResponseEntity.ok(estoqueProdutos);
    }

   //listar todos os produtos em estoque sem paginação
   @GetMapping("/estoque-produtos/all")
   public ResponseEntity<List<EstoqueProduto>> getAllEstoqueProdutos() {
       List<EstoqueProduto> estoqueProdutos = estoqueProdutoRepository.findAll();
       return ResponseEntity.ok(estoqueProdutos);
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

     @GetMapping("/estoque-produtos/{id}")
    public ResponseEntity<EstoqueProduto> getEstoqueProdutoId(@PathVariable Long id) {
        EstoqueProduto estoqueProduto = estoqueProdutoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estoque Produto não encontrado" + id));
                return ResponseEntity.ok(estoqueProduto);
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

     // delete estoque produto
    @DeleteMapping("/estoque-produtos/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteEstoqueProduto(@PathVariable Long id) {
        EstoqueProduto estoqueProduto = estoqueProdutoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estoque Produto não encontrado" + id));
        estoqueProdutoRepository.delete(estoqueProduto);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
    
}
