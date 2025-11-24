package br.edu.univille.poo.busque_aqui.service;

import br.edu.univille.poo.busque_aqui.dto.ComparacaoCard;
import br.edu.univille.poo.busque_aqui.entity.SearchLog;
import br.edu.univille.poo.busque_aqui.repository.SearchLogRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    private final ChatClient chatClient;
    private final SearchLogRepository searchLogRepository;
    private final ObjectMapper objectMapper;

    public ProdutoService(ChatClient chatClient, SearchLogRepository searchLogRepository) {
        this.chatClient = chatClient;
        this.searchLogRepository = searchLogRepository;
        this.objectMapper = new ObjectMapper();
    }

    public List<ComparacaoCard> comparar(String produto1, String produto2) {


        // o prompt
        String promptTemplateString = """
            Você é um especialista em comparação de produtos. Sua tarefa é analisar e comparar os produtos %s e %s em 5 a 8 recursos chave.

            Você DEVE responder APENAS com um array JSON estrito (sem texto introdutório ou conclusivo) que se enquadre na lista de objetos JSON.

            O formato JSON esperado é:
            [{"recurso": "Nome do Recurso", "valorProduto1": "Valor do Produto 1", "valorProduto2": "Valor do Produto 2", "melhorEm": "Produto 1, Produto 2 ou Empate"}]

            A coluna "melhorEm" deve conter um dos três valores exatos: "Produto 1", "Produto 2" ou "Empate".
            """;

        // substitui os %s pelos valores reais de produto1 e produto2
        String prompt = String.format(promptTemplateString, produto1, produto2);

        System.out.println("PROMPT ENVIADO AO GEMINI (String.format)");
        System.out.println(prompt);


        // faz a chamada ao Gemini e desserializa a Entidade
        // injeta a string prompt diretamente no .user()
        List<ComparacaoCard> cards = chatClient.prompt()
                .user(prompt)
                .call()
                .entity(new ParameterizedTypeReference<List<ComparacaoCard>>() {});

        // salva no banco de dados
        if (cards != null && !cards.isEmpty()) {
            try {
                String jsonResult = objectMapper.writeValueAsString(cards);

                // LOG DE SISTEMA
                System.out.println("\n DADOS PRONTOS PARA SALVAR NO BANCO");
                System.out.println("JSON Resultante: " + jsonResult);

                SearchLog log = new SearchLog();
                log.setProduto1(produto1);
                log.setProduto2(produto2);
                log.setJsonResult(jsonResult);

                searchLogRepository.save(log);
            } catch (JsonProcessingException e) {
                System.err.println("Erro ao serializar JSON para o log: " + e.getMessage());
            }
        } else {
            System.out.println("\n NENHUM DADO RECEBIDO DO GEMINI"); // para ter certeza que deu certo
        }

        return cards;
    }

    //return uma lista de objetos SearchLog
    public List<SearchLog> buscarHistorico() {
        // busca todos os logs
        return searchLogRepository.findAll();
    }
}