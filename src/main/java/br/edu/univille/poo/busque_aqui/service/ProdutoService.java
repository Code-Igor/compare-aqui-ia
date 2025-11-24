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

        // 1. DEFINE O PROMPT como uma string de formato simples (Text Block)
        // Usamos %s para placeholders que serão preenchidos por String.format
        String promptTemplateString = """
            Você é um especialista em comparação de produtos. Sua tarefa é analisar e comparar os produtos %s e %s em 5 a 8 recursos chave.

            Você DEVE responder APENAS com um array JSON estrito (sem texto introdutório ou conclusivo) que se enquadre na lista de objetos JSON.

            O formato JSON esperado é:
            [{"recurso": "Nome do Recurso", "valorProduto1": "Valor do Produto 1", "valorProduto2": "Valor do Produto 2", "melhorEm": "Produto 1, Produto 2 ou Empate"}]

            A coluna "melhorEm" deve conter um dos três valores exatos: "Produto 1", "Produto 2" ou "Empate".
            """;

        // 2. FORMATA O PROMPT: Substitui os %s pelos valores reais de produto1 e produto2
        String prompt = String.format(promptTemplateString, produto1, produto2);

        // LOG DE SISTEMA: Imprime o prompt final enviado ao Gemini
        System.out.println("--- 🔎 PROMPT ENVIADO AO GEMINI (String.format) ---");
        System.out.println(prompt);
        System.out.println("-----------------------------------------------------");

        // 3. Faz a chamada ao Gemini e desserializa a Entidade
        // Injete a string 'prompt' diretamente no .user()
        List<ComparacaoCard> cards = chatClient.prompt()
                .user(prompt)
                .call()
                .entity(new ParameterizedTypeReference<List<ComparacaoCard>>() {});

        // 4. Salva no banco de dados (lógica de persistência)
        if (cards != null && !cards.isEmpty()) {
            try {
                String jsonResult = objectMapper.writeValueAsString(cards);

                // LOG DE SISTEMA: Imprime o JSON COMPLETO que será salvo
                System.out.println("\n--- ✅ DADOS PRONTOS PARA SALVAR NO BANCO ---");
                System.out.println("JSON Resultante: " + jsonResult);
                System.out.println("--------------------------------------------");

                SearchLog log = new SearchLog(produto1, produto2, jsonResult);
                searchLogRepository.save(log);
            } catch (JsonProcessingException e) {
                System.err.println("Erro ao serializar JSON para o log: " + e.getMessage());
            }
        } else {
            System.out.println("\n--- ⚠️ ALERTA: NENHUM DADO RECEBIDO DO GEMINI ---");
        }

        return cards;
    }
}