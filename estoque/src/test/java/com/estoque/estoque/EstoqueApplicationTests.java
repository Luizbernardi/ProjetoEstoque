package com.estoque.estoque;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.estoque.estoque.model.Estoque;
import com.estoque.estoque.model.EstoqueProduto;
import com.estoque.estoque.model.Produto;
import com.estoque.estoque.repository.EstoqueProdutoRepository;
import com.estoque.estoque.repository.EstoqueRepository;
import com.estoque.estoque.repository.ProdutoRepository;
import com.estoque.estoque.service.EstoqueService;
import com.estoque.estoque.service.ProdutoService;

@SpringBootTest
class EstoqueApplicationTests {

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private EstoqueRepository estoqueRepository;

    @Mock
    private EstoqueProdutoRepository estoqueProdutoRepository;

    @InjectMocks
    private ProdutoService produtoService;

    @InjectMocks
    private EstoqueService estoqueService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void contextLoads() {
    }

    @Test
    void testRegistroEntradaProduto() {
        Produto produto = new Produto();
        produto.setId(1L);
		produto.setNome("Produto Teste");
        produto.setDescricao("Descrição do Produto Teste");
        produto.setPreco(100.0);
        produto.setDataEntrada(LocalDateTime.now());

        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
        when(produtoRepository.save(any(Produto.class))).thenAnswer(i -> i.getArgument(0));

        LocalDateTime dataEntrada = produtoService.registroEntradaProduto(1L);

        assertNotNull(dataEntrada);
        assertEquals(LocalDateTime.now().getDayOfYear(), dataEntrada.getDayOfYear());
        assertEquals(LocalDateTime.now().getHour(), dataEntrada.getHour());
    }

    @Test
    void testRegistroSaidaProduto() {
        Produto produto = new Produto();
        produto.setId(1L);
        produto.setDataSaida(LocalDateTime.now());

        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
        when(produtoRepository.save(any(Produto.class))).thenAnswer(i -> i.getArgument(0));

        LocalDateTime dataSaida = produtoService.registroSaidaProduto(1L);

        assertNotNull(dataSaida);
        assertEquals(LocalDateTime.now().getDayOfYear(), dataSaida.getDayOfYear());
        assertEquals(LocalDateTime.now().getHour(), dataSaida.getHour());
    }

    @Test
    void testRegistroEntradaProdutoProdutoNaoEncontrado() {
        when(produtoRepository.findById(1L)).thenReturn(Optional.empty());

        LocalDateTime dataEntrada = produtoService.registroEntradaProduto(1L);

        assertNull(dataEntrada);
    }

    @Test
    void testRegistroSaidaProdutoProdutoNaoEncontrado() {
        when(produtoRepository.findById(1L)).thenReturn(Optional.empty());

        LocalDateTime dataSaida = produtoService.registroSaidaProduto(1L);

        assertNull(dataSaida);
    }

    @Test
    void testVerificarEstoqueProduto() {
        Estoque estoque = new Estoque();
        estoque.setId(1L);
        Produto produto = new Produto();
        produto.setId(1L);
        EstoqueProduto estoqueProduto = new EstoqueProduto(1L, estoque, produto, 10);

        when(estoqueRepository.findById(1L)).thenReturn(Optional.of(estoque));
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
        when(estoqueProdutoRepository.findByEstoqueAndProduto(estoque, produto)).thenReturn(Optional.of(estoqueProduto));

        EstoqueProduto result = estoqueService.verificarEstoqueProduto(1L, 1L);

        assertNotNull(result);
        assertEquals(10, result.getQuantidade());
    }

    @Test
    void testVerificarEstoqueTotal() {
        Estoque estoque = new Estoque();
        estoque.setId(1L);
        Produto produto = new Produto();
        produto.setId(1L);
        EstoqueProduto estoqueProduto = new EstoqueProduto(1L, estoque, produto, 10);

        when(estoqueProdutoRepository.findAll()).thenReturn(Arrays.asList(estoqueProduto));

        List<EstoqueProduto> result = estoqueService.verificarEstoqueTotal();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(10, result.get(0).getQuantidade());
    }

    @Test
    void testCalcularValorTotalEstoque() {
        Estoque estoque = new Estoque();
        estoque.setId(1L);
        Produto produto = new Produto();
        produto.setId(1L);
        produto.setPreco(100.0);
        EstoqueProduto estoqueProduto = new EstoqueProduto(1L, estoque, produto, 10);

        when(estoqueProdutoRepository.findAll()).thenReturn(Arrays.asList(estoqueProduto));

        Double valorTotal = estoqueService.calcularValorTotalEstoque();

        assertEquals(1000.0, valorTotal);
    }

    @Test
    void testAtualizarEstoqueProduto() {
        Estoque estoque = new Estoque();
        estoque.setId(1L);
        Produto produto = new Produto();
        produto.setId(1L);
        EstoqueProduto estoqueProduto = new EstoqueProduto(1L, estoque, produto, 10);

        when(estoqueRepository.findById(1L)).thenReturn(Optional.of(estoque));
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
        when(estoqueProdutoRepository.findByEstoqueAndProduto(estoque, produto)).thenReturn(Optional.of(estoqueProduto));
        when(estoqueProdutoRepository.save(any(EstoqueProduto.class))).thenAnswer(i -> i.getArgument(0));

        EstoqueProduto result = estoqueService.atualizarEstoqueProduto(1L, 1L, 20);

        assertNotNull(result);
        assertEquals(20, result.getQuantidade());
    }

    @Test
    void testRegistroEntradaEstoque() {
        Estoque estoque = new Estoque();
        estoque.setId(1L);
        estoque.setDataEntrada(null);

        when(estoqueRepository.findById(1L)).thenReturn(Optional.of(estoque));
        when(estoqueRepository.save(any(Estoque.class))).thenAnswer(i -> i.getArgument(0));

        LocalDateTime dataEntrada = estoqueService.registroEntradaProduto(1L);

        assertNotNull(dataEntrada);
        assertEquals(LocalDateTime.now().getDayOfYear(), dataEntrada.getDayOfYear());
        assertEquals(LocalDateTime.now().getHour(), dataEntrada.getHour());
    }

    @Test
    void testRegistroSaidaEstoque() {
        Estoque estoque = new Estoque();
        estoque.setId(1L);
        estoque.setDataSaida(null);

        when(estoqueRepository.findById(1L)).thenReturn(Optional.of(estoque));
        when(estoqueRepository.save(any(Estoque.class))).thenAnswer(i -> i.getArgument(0));

        LocalDateTime dataSaida = estoqueService.registroSaidaProduto(1L);

        assertNotNull(dataSaida);
        assertEquals(LocalDateTime.now().getDayOfYear(), dataSaida.getDayOfYear());
        assertEquals(LocalDateTime.now().getHour(), dataSaida.getHour());
    }
}