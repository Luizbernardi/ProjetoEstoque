package com.estoque.estoque.controllers.estoque;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.estoque.estoque.model.Estoque;
import com.estoque.estoque.model.EstoqueProduto;
import com.estoque.estoque.model.Produto;
import com.estoque.estoque.repository.EstoqueProdutoRepository;
import com.estoque.estoque.repository.EstoqueRepository;
import com.estoque.estoque.repository.ProdutoRepository;
import com.estoque.estoque.service.EstoqueProdutoService;
import com.estoque.estoque.service.EstoqueService;
import com.estoque.estoque.service.ProdutoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/estoque")
public class EstoqueController {

    @Autowired
    private EstoqueRepository estoqueRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private EstoqueProdutoRepository estoqueProdutoRepository;

    @Autowired
    private EstoqueService estoqueService;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private EstoqueProdutoService estoqueProdutoService;

    @GetMapping("/cadastro-produto")
    public ModelAndView cadastroProduto(Produto produto) {
        ModelAndView mv = new ModelAndView("estoque/cadastro-produto");
        mv.addObject("produto", new Produto());
        return mv;
    }

    @PostMapping("/cadastro-produto")
    public ModelAndView cadastrarProduto(@ModelAttribute Produto produto, Model model) {
        produtoRepository.save(produto);
        model.addAttribute("message", "Produto cadastrado com sucesso!");
        return new ModelAndView("estoque/cadastro-produto");
    }

    @GetMapping("/cadastro-estoque")
    public ModelAndView cadastroEstoque(Estoque estoque) {
        ModelAndView mv = new ModelAndView("estoque/cadastro-estoque");
        mv.addObject("estoque", new Estoque());
        return mv;
    }

    @PostMapping("/cadastro-estoque")
    public ModelAndView cadastrarEstoque(@ModelAttribute Estoque estoque, Model model) {
        estoqueRepository.save(estoque);
        model.addAttribute("message", "Estoque cadastrado com sucesso!");
        return new ModelAndView("estoque/cadastro-estoque");
    }


    @GetMapping("/cadastro-produto-estoque")
    public ModelAndView cadastroProdutoEstoque(Model model) {
        ModelAndView mv = new ModelAndView("estoque/cadastro-produto-estoque");
        mv.addObject("produtos", produtoRepository.findAll());
        mv.addObject("estoques", estoqueRepository.findAll());
        return mv;
    }

    @PostMapping("/cadastro-produto-estoque")
    public ModelAndView cadastrarProdutoEmEstoque(@RequestParam Long estoqueId, @RequestParam Long produtoId, @RequestParam int quantidade, Model model) {
        String message = estoqueService.cadastrarProdutoEmEstoque(estoqueId, produtoId, quantidade);
        model.addAttribute("message", message);
        model.addAttribute("produtos", produtoRepository.findAll());
        model.addAttribute("estoques", estoqueRepository.findAll());
        return new ModelAndView("estoque/cadastro-produto-estoque");
    }

  
    @GetMapping("/list-estoque")
    public ModelAndView estoquelist() {
        ModelAndView mv = new ModelAndView("estoque/list-estoque");
        mv.addObject("estoques", estoqueRepository.findAll());
        return mv;
    }

    @GetMapping("/list-produto")
    public ModelAndView produtolist() {
        ModelAndView mv = new ModelAndView("estoque/list-produto");
        mv.addObject("produtos", produtoRepository.findAll());
        return mv;
    }
    
    @GetMapping("/list-produto-estoque")
    public ModelAndView produtoestoquelist() {
        ModelAndView mv = new ModelAndView("estoque/list-produto-estoque");
        List<EstoqueProduto> estoqueProdutos = estoqueService.verificarEstoqueTotal();
        mv.addObject("produtoestoque", estoqueProdutos);

        // Calcular o preço total de cada produto no estoque
        List<Double> precosTotaisProdutos = estoqueProdutos.stream()
                .map(ep -> ep.getQuantidade() * ep.getProduto().getPreco())
                .collect(Collectors.toList());
        mv.addObject("precosTotaisProdutos", precosTotaisProdutos);

        // Calcular o preço total do estoque
        double precoTotalEstoque = estoqueProdutos.stream()
                .mapToDouble(ep -> ep.getQuantidade() * ep.getProduto().getPreco())
                .sum();
        mv.addObject("precoTotalEstoque", precoTotalEstoque);

        return mv;
    }
  

    @GetMapping("/preco-total-produto")
    public double calcularPrecoTotalProdutoNoEstoque(@RequestParam Long estoqueId, @RequestParam Long produtoId) {
        return estoqueService.calcularPrecoTotalProdutoNoEstoque(estoqueId, produtoId);
    }

    @GetMapping("/preco-total-estoque")
    public double calcularPrecoTotalEstoque(@RequestParam Long estoqueId) {
        return estoqueService.calcularPrecoTotalEstoque(estoqueId);
    }

    @GetMapping("/excluir-produto/{id}")
    public String excluirProduto(@PathVariable("id") Long id) {
        produtoRepository.deleteById(id);
        return "redirect:/estoque/list-produto";
    }

    @GetMapping("/excluir-estoque/{id}")
    public String excluirEstoque(@PathVariable("id") Long id) {
        estoqueRepository.deleteById(id);
        return "redirect:/estoque/list-estoque";
    }

    @GetMapping("/excluir-produto-estoque/{id}")
    public String excluirProdutoEstoque(@PathVariable("id") Long id) {
        estoqueProdutoRepository.deleteById(id);
        return "redirect:/estoque/list-produto-estoque";
    }

    @GetMapping("/editar-produto/{id}")
    public ModelAndView editarProduto(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView("estoque/editar-produto");
        Produto produto = produtoRepository.findById(id).get();
        mv.addObject("produto", produto);
        return mv;
    }

    @PostMapping("/editar-produto")
    public String editarProduto(@ModelAttribute Produto produto) {
        produtoRepository.save(produto);
        return "redirect:/estoque/list-produto";
    }

    @GetMapping("/editar-estoque/{id}")
    public ModelAndView editarEstoque(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView("estoque/editar-estoque");
        Estoque estoque = estoqueRepository.findById(id).get();
        mv.addObject("estoque", estoque);
        return mv;
    }

    @PostMapping("/editar-estoque")
    public String editarEstoque(@ModelAttribute Estoque estoque) {
        estoqueRepository.save(estoque);
        return "redirect:/estoque/list-estoque";
    }

    @GetMapping("/editar-produto-estoque/{id}")
    public ModelAndView editarProdutoEstoque(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView("estoque/editar-produto-estoque");
        EstoqueProduto estoqueProduto = estoqueProdutoRepository.findById(id).get();
        mv.addObject("estoqueProduto", estoqueProduto);
        return mv;
    }

    @PostMapping("/editar-produto-estoque")
    public String editarProdutoEstoque(@ModelAttribute EstoqueProduto estoqueProduto) {
        estoqueProdutoRepository.save(estoqueProduto);
        return "redirect:/estoque/list-produto-estoque";
    }


    
   


}
