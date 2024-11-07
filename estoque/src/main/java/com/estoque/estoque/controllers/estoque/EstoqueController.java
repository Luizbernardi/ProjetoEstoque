package com.estoque.estoque.controllers.estoque;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.estoque.estoque.model.Estoque;
import com.estoque.estoque.repository.EstoqueRepository;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/estoque")
@CrossOrigin(origins = "http://localhost:4200")
public class EstoqueController {

    @Autowired
    private EstoqueRepository estoqueRepository;

    //listar todos os estoques com paginação
    @GetMapping("/estoques")
    public ResponseEntity<Page<Estoque>> getAllEstoques(@RequestParam int page, @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Estoque> estoques = estoqueRepository.findAll(pageable);
        return ResponseEntity.ok(estoques);
    }

    //Criando Estoque Rest APi
    @PostMapping("/estoques")
    public Estoque createEstoque(@RequestBody Estoque estoque) {
        return estoqueRepository.save(estoque);
    }

    //get estoque por id
    @GetMapping("/estoques/{id}")
    public ResponseEntity<Estoque> getEstoqueById(@PathVariable Long id) {
        Estoque estoque = estoqueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estoque não encontrado" + id));
                return ResponseEntity.ok(estoque);
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
    

    // delete estoque
    @DeleteMapping("/estoques/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteEstoque(@PathVariable Long id) {
        Estoque estoque = estoqueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estoque não encontrado" + id));
        estoqueRepository.delete(estoque);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
    
}


