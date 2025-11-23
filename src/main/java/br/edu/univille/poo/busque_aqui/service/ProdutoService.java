package br.edu.univille.poo.busque_aqui.service;

import br.edu.univille.poo.busque_aqui.dto.ComparacaoCard;
import br.edu.univille.poo.busque_aqui.entity.SearchLog;
import br.edu.univille.poo.busque_aqui.repository.SearchLogRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        // 1. DEFINE O PROMPT USANDO CONCATENAÇÃO DE STRINGS PADRÃO.
        // Isso resolve o conflito de sintaxe do StringTemplate.
        String promptTemplateString =
                "Você é um especialista em comparação de produtos. Sua tarefa é analisar e comparar os produtos {produto1} e {produto2} em 5 a 8 recursos chave." +
                        "\n\nVocê DEVE responder APENAS com um array JSON estrito (sem texto introdutório ou conclusivo) que se enquadre na lista de objetos JSON." +
                        "\n\nO formato JSON esperado é:" +
                        "\n[\"recurso\": \"Nome do Recurso\", \"valorProduto1\": \"Valor do Produto 1\", \"valorProduto2\": \"Valor do Produto 2\", \"melhorEm\": \"Produto 1, Produto 2 ou Empate\"]" +
                        "\n\nA coluna \"melhorEm\" deve conter um dos três valores exatos: \"Produto 1\", \"Produto 2\" ou \"Empate\".";

        // prepara o map de variáveis
        Map<String, Object> promptParameters = new HashMap<>();
        promptParameters.put("produto1", produto1);
        promptParameters.put("produto2", produto2);

        // 3. Cria e renderiza o prompt
        PromptTemplate promptTemplate = new PromptTemplate(promptTemplateString);
        String prompt = promptTemplate.render(promptParameters);

        // 4. Faz a chamada ao Gemini e desserializa a Entidade
        // Se a chave API estiver correta, esta parte funcionará.
        List<ComparacaoCard> cards = chatClient.prompt()
                .user(prompt)
                .call()
                .entity(new ParameterizedTypeReference<List<ComparacaoCard>>() {});

        // 5. Salva no banco de dados (lógica de persistência)
        if (cards != null && !cards.isEmpty()) {
            try {
                String jsonResult = objectMapper.writeValueAsString(cards);

                System.out.println("\n DADOS PRONTOS PARA SALVAR NO BANCO");
                System.out.println("Produto 1: " + produto1);
                System.out.println("Produto 2: " + produto2);
                System.out.println("JSON resultante: " + jsonResult);

                SearchLog log = new SearchLog(produto1, produto2, jsonResult);
                searchLogRepository.save(log);
            } catch (JsonProcessingException e) {
                System.err.println("Erro ao serializar JSON para o log: " + e.getMessage());
            }
        }
        return cards;
    }
}