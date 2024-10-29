package com.estoque.estoque;

import com.estoque.estoque.controllers.estoque.EstoqueController;
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
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class EstoqueControllerTest {

    @Mock
    private EstoqueRepository estoqueRepository;

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private EstoqueProdutoRepository estoqueProdutoRepository;

    @Mock
    private EstoqueService estoqueService;

    @InjectMocks
    private EstoqueController estoqueController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCadastrarProduto() {
        Produto produto = new Produto();
        Model model = mock(Model.class);
        ModelAndView mv = estoqueController.cadastrarProduto(produto, model);
        verify(produtoRepository, times(1)).save(produto);
        verify(model, times(1)).addAttribute("message", "Produto cadastrado com sucesso!");
        assertEquals("estoque/cadastro-produto", mv.getViewName());
    }
  
    @Test
    public void testCadastrarEstoque() {
        Estoque estoque = new Estoque();
        Model model = mock(Model.class);
        ModelAndView mv = estoqueController.cadastrarEstoque(estoque, model);
        verify(estoqueRepository, times(1)).save(estoque);
        verify(model, times(1)).addAttribute("message", "Estoque cadastrado com sucesso!");
        assertEquals("estoque/cadastro-estoque", mv.getViewName());
    }

    @Test
    public void testCadastroProdutoEstoque() {
        when(produtoRepository.findAll()).thenReturn(Arrays.asList(new Produto()));
        when(estoqueRepository.findAll()).thenReturn(Arrays.asList(new Estoque()));
        ModelAndView mv = estoqueController.cadastroProdutoEstoque(mock(Model.class));
        assertEquals("estoque/cadastro-produto-estoque", mv.getViewName());
        assertEquals(1, ((List<?>) mv.getModel().get("produtos")).size());
        assertEquals(1, ((List<?>) mv.getModel().get("estoques")).size());
    }

    @Test
    public void testCadastrarProdutoEmEstoque() {
        Model model = mock(Model.class);
        when(estoqueService.cadastrarProdutoEmEstoque(anyLong(), anyLong(), anyInt())).thenReturn("Produto cadastrado no estoque com sucesso!");
        ModelAndView mv = estoqueController.cadastrarProdutoEmEstoque(1L, 1L, 10, model);
        verify(estoqueService, times(1)).cadastrarProdutoEmEstoque(1L, 1L, 10);
        verify(model, times(1)).addAttribute("message", "Produto cadastrado no estoque com sucesso!");
        assertEquals("estoque/cadastro-produto-estoque", mv.getViewName());
    }

    @Test
    public void testExcluirProduto() {
        String result = estoqueController.excluirProduto(1L);
        verify(produtoRepository, times(1)).deleteById(1L);
        assertEquals("redirect:/estoque/list-produto", result);
    }

    @Test
    public void testExcluirEstoque() {
        EstoqueProduto estoqueProduto = new EstoqueProduto();
        when(estoqueProdutoRepository.findByProdutoId(1L)).thenReturn(Arrays.asList(estoqueProduto));
        String result = estoqueController.excluirEstoque(1L);
        verify(estoqueProdutoRepository, times(1)).deleteAll(Arrays.asList(estoqueProduto));
        verify(estoqueRepository, times(1)).deleteById(1L);
        assertEquals("redirect:/estoque/list-estoque", result);
    }

    @Test
    public void testExcluirProdutoEstoque() {
        EstoqueProduto estoqueProduto = new EstoqueProduto();
        when(estoqueProdutoRepository.findByEstoqueId(1L)).thenReturn(Arrays.asList(estoqueProduto));
        String result = estoqueController.excluirProdutoEstoque(1L);
        verify(estoqueProdutoRepository, times(1)).deleteAll(Arrays.asList(estoqueProduto));
        verify(estoqueProdutoRepository, times(1)).deleteById(1L);
        assertEquals("redirect:/estoque/list-produto-estoque", result);
    }

    @Test
    public void testEditarProduto() {
        Produto produto = new Produto();
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
        ModelAndView mv = estoqueController.editarProduto(1L);
        assertEquals("estoque/editar-produto", mv.getViewName());
        assertEquals(produto, mv.getModel().get("produto"));
    }

    @Test
    public void testEditarEstoque() {
        Estoque estoque = new Estoque();
        when(estoqueRepository.findById(1L)).thenReturn(Optional.of(estoque));
        ModelAndView mv = estoqueController.editarEstoque(1L);
        assertEquals("estoque/editar-estoque", mv.getViewName());
        assertEquals(estoque, mv.getModel().get("estoque"));
    }

    @Test
    public void testEditarProdutoEstoque() {
        EstoqueProduto estoqueProduto = new EstoqueProduto();
        when(estoqueProdutoRepository.findById(1L)).thenReturn(Optional.of(estoqueProduto));
        ModelAndView mv = estoqueController.editarProdutoEstoque(1L);
        assertEquals("estoque/editar-produto-estoque", mv.getViewName());
        assertEquals(estoqueProduto, mv.getModel().get("estoqueProduto"));
    }

    @Test
    public void testProdutoPorId() {
        Produto produto = new Produto();
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
        ModelAndView mv = estoqueController.produtoPorId(1L);
        assertEquals("estoque/produto", mv.getViewName());
        assertEquals(produto, mv.getModel().get("produto"));
    }

    @Test
    public void testEstoquePorId() {
        Estoque estoque = new Estoque();
        when(estoqueRepository.findById(1L)).thenReturn(Optional.of(estoque));
        ModelAndView mv = estoqueController.estoquePorId(1L);
        assertEquals("estoque/estoque", mv.getViewName());
        assertEquals(estoque, mv.getModel().get("estoque"));
    }

    @Test
    public void testProdutoPorNome() {
        Produto produto = new Produto();
        when(produtoRepository.findByNome("nome")).thenReturn(produto);
        ModelAndView mv = estoqueController.produtoPorNome("nome");
        assertEquals("estoque/produto", mv.getViewName());
        assertEquals(produto, mv.getModel().get("produto"));
    }

    @Test
    public void testSearchPorNome() {
        Produto produto = new Produto();
        when(produtoRepository.findByNome("nome")).thenReturn(produto);
        ModelAndView mv = estoqueController.SearchPorNome("nome");
        assertEquals("estoque/produto", mv.getViewName());
        assertEquals(produto, mv.getModel().get("produto"));
    }

    @Test
    public void testEstoquePorNome() {
        Estoque estoque = new Estoque();
        when(estoqueRepository.findByNome("nome")).thenReturn(estoque);
        ModelAndView mv = estoqueController.estoquePorNome("nome");
        assertEquals("estoque/estoque", mv.getViewName());
        assertEquals(estoque, mv.getModel().get("estoque"));
    }

    @Test
    public void testHome() {
        ModelAndView mv = estoqueController.home();
        assertEquals("index", mv.getViewName());
    }
}