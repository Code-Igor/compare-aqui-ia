package br.edu.univille.poo.busque_aqui;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {

    /**
     * Define o ChatClient como um Bean do Spring usando o Builder injetado automaticamente.
     * Isso garante que a instância do ChatClient esteja disponível para injeção
     * no ProdutoService.
     */
    @Bean
    public ChatClient chatClient(ChatClient.Builder builder) {
        // O builder já está configurado com a chave de API do application.properties
        return builder.build();
    }
}