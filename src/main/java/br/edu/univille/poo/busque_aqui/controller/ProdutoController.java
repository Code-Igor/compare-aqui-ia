package br.edu.univille.poo.busque_aqui.controller;

import br.edu.univille.poo.busque_aqui.dto.ComparacaoCard;
import br.edu.univille.poo.busque_aqui.entity.SearchLog;
import br.edu.univille.poo.busque_aqui.service.ProdutoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    // mapeia a página inicial
    @GetMapping("/")
    public String index(Model model) {
        // Inicializa as variáveis para evitar o erro EL1001E (null para boolean) no Thymeleaf
        model.addAttribute("erro", null);
        model.addAttribute("cards", null);
        model.addAttribute("produtoPesquisado1", "");
        model.addAttribute("produtoPesquisado2", "");

        return "index";
    }

    // mapeia a requisição de comparação
    @GetMapping("/comparar")
    public String compararProdutos(
            @RequestParam(name = "produto1", required = true) String produto1,
            @RequestParam(name = "produto2", required = true) String produto2,
            Model model) {

        model.addAttribute("produtoPesquisado1", produto1);
        model.addAttribute("produtoPesquisado2", produto2);

        // garante que 'cards' e 'erro' sejam re-inicializados
        model.addAttribute("cards", null);
        model.addAttribute("erro", null);

        try {
            List<ComparacaoCard> cards = produtoService.comparar(produto1, produto2);
            model.addAttribute("cards", cards);

        } catch (Exception e) {
            model.addAttribute("erro", "Erro ao processar a comparação com Gemini: " + e.getMessage());
            e.printStackTrace();
        }

        return "index";
    }


    @GetMapping("/historico")
    public String mostrarHistorico(Model model) {
        try {
            //chama o serviço para buscar todos os logs
            List<SearchLog> logs = produtoService.buscarHistorico();

            // adiciona a lista de logs ao modelo sob o nome 'historicoLogs'
            model.addAttribute("historicoLogs", logs);

            // somente para verificar
            System.out.println("Histórico carregado: " + logs.size() + " itens.");

        } catch (Exception e) {
            model.addAttribute("erro", "Erro ao carregar o histórico: " + e.getMessage());
            model.addAttribute("historicoLogs", List.of()); // Garante que a lista não é nula
            e.printStackTrace();
        }
        return "historico";
    }
}