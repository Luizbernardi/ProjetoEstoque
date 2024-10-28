package com.estoque.estoque.controllers.estoque;

import java.util.Optional;
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
import com.estoque.estoque.service.EstoqueService;
import com.estoque.estoque.service.ProdutoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    public ModelAndView cadastrarProdutoEmEstoque(@RequestParam Long estoqueId, @RequestParam Long produtoId,
            @RequestParam int quantidade, Model model) {
        Optional<Estoque> estoqueOptional = estoqueRepository.findById(estoqueId);
        Optional<Produto> produtoOptional = produtoRepository.findById(produtoId);

        if (estoqueOptional.isPresent() && produtoOptional.isPresent()) {
            Estoque estoque = estoqueOptional.get();
            Produto produto = produtoOptional.get();
            EstoqueProduto estoqueProduto = new EstoqueProduto(null, estoque, produto, quantidade);
            estoqueProdutoRepository.save(estoqueProduto);
            model.addAttribute("message", "Produto cadastrado no estoque com sucesso!");
        } else {
            model.addAttribute("message", "Estoque ou Produto não encontrado.");
        }

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
        mv.addObject("produtoestoque", estoqueProdutoRepository.findAll());
        return mv;
    }
}
