package com.estoque.estoque;

import com.estoque.estoque.model.Estoque;
import com.estoque.estoque.model.EstoqueProduto;
import com.estoque.estoque.model.Produto;
import com.estoque.estoque.repository.EstoqueProdutoRepository;
import com.estoque.estoque.repository.EstoqueRepository;
import com.estoque.estoque.repository.ProdutoRepository;
import com.estoque.estoque.service.EstoqueService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class EstoqueServiceTest {

    @Mock
    private EstoqueRepository estoqueRepository;

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private EstoqueProdutoRepository estoqueProdutoRepository;

    @InjectMocks
    private EstoqueService estoqueService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testVerificarEstoqueTotal() {
        EstoqueProduto estoqueProduto1 = new EstoqueProduto();
        EstoqueProduto estoqueProduto2 = new EstoqueProduto();
        List<EstoqueProduto> estoqueProdutos = Arrays.asList(estoqueProduto1, estoqueProduto2);

        when(estoqueProdutoRepository.findAll()).thenReturn(estoqueProdutos);

        List<EstoqueProduto> result = estoqueService.verificarEstoqueTotal();

        assertEquals(2, result.size());
        verify(estoqueProdutoRepository, times(1)).findAll();
    }

    @Test
    public void testCalcularPrecoTotalProdutoNoEstoque() {
        Estoque estoque = new Estoque();
        Produto produto = new Produto();
        produto.setPreco(10.0);
        EstoqueProduto estoqueProduto = new EstoqueProduto();
        estoqueProduto.setQuantidade(5);
        estoqueProduto.setProduto(produto);

        when(estoqueRepository.findById(1L)).thenReturn(Optional.of(estoque));
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
        when(estoqueProdutoRepository.findByEstoqueAndProduto(estoque, produto)).thenReturn(Optional.of(estoqueProduto));

        double result = estoqueService.calcularPrecoTotalProdutoNoEstoque(1L, 1L);

        assertEquals(50.0, result);
        verify(estoqueRepository, times(1)).findById(1L);
        verify(produtoRepository, times(1)).findById(1L);
        verify(estoqueProdutoRepository, times(1)).findByEstoqueAndProduto(estoque, produto);
    }

    @Test
    public void testCalcularPrecoTotalEstoque() {
        Estoque estoque = new Estoque();
        Produto produto1 = new Produto();
        produto1.setPreco(10.0);
        Produto produto2 = new Produto();
        produto2.setPreco(20.0);
        EstoqueProduto estoqueProduto1 = new EstoqueProduto();
        estoqueProduto1.setQuantidade(5);
        estoqueProduto1.setProduto(produto1);
        EstoqueProduto estoqueProduto2 = new EstoqueProduto();
        estoqueProduto2.setQuantidade(3);
        estoqueProduto2.setProduto(produto2);
        List<EstoqueProduto> estoqueProdutos = Arrays.asList(estoqueProduto1, estoqueProduto2);

        when(estoqueRepository.findById(1L)).thenReturn(Optional.of(estoque));
        when(estoqueProdutoRepository.findByEstoque(estoque)).thenReturn(estoqueProdutos);

        double result = estoqueService.calcularPrecoTotalEstoque(1L);

        assertEquals(110.0, result);
        verify(estoqueRepository, times(1)).findById(1L);
        verify(estoqueProdutoRepository, times(1)).findByEstoque(estoque);
    }

    @Test
    public void testCadastrarProdutoEmEstoqueNovo() {
        Estoque estoque = new Estoque();
        Produto produto = new Produto();
        EstoqueProduto estoqueProduto = new EstoqueProduto(null, estoque, produto, 10);

        when(estoqueRepository.findById(1L)).thenReturn(Optional.of(estoque));
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
        when(estoqueProdutoRepository.findByEstoqueAndProduto(estoque, produto)).thenReturn(Optional.empty());
        when(estoqueProdutoRepository.save(any(EstoqueProduto.class))).thenReturn(estoqueProduto);

        String result = estoqueService.cadastrarProdutoEmEstoque(1L, 1L, 10);

        assertEquals("Produto cadastrado no estoque com sucesso!", result);
        verify(estoqueRepository, times(1)).findById(1L);
        verify(produtoRepository, times(1)).findById(1L);
        verify(estoqueProdutoRepository, times(1)).findByEstoqueAndProduto(estoque, produto);
        verify(estoqueProdutoRepository, times(1)).save(any(EstoqueProduto.class));
    }

    @Test
    public void testCadastrarProdutoEmEstoqueExistente() {
        Estoque estoque = new Estoque();
        Produto produto = new Produto();
        EstoqueProduto estoqueProduto = new EstoqueProduto(null, estoque, produto, 10);

        when(estoqueRepository.findById(1L)).thenReturn(Optional.of(estoque));
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
        when(estoqueProdutoRepository.findByEstoqueAndProduto(estoque, produto)).thenReturn(Optional.of(estoqueProduto));

        String result = estoqueService.cadastrarProdutoEmEstoque(1L, 1L, 5);

        assertEquals("Quantidade do produto atualizada no estoque com sucesso!", result);
        verify(estoqueRepository, times(1)).findById(1L);
        verify(produtoRepository, times(1)).findById(1L);
        verify(estoqueProdutoRepository, times(1)).findByEstoqueAndProduto(estoque, produto);
        verify(estoqueProdutoRepository, times(1)).save(estoqueProduto);
    }
}