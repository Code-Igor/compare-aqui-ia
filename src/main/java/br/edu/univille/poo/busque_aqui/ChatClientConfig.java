package br.edu.univille.poo.busque_aqui;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// fui obrigado a criar essa classe devido a um erro que me impedia de rodar a aplicação
// aparentemente o spring não conseguia encontrar o chatclient
@Configuration
public class ChatClientConfig {


      //define o ChatClient como um bean do spring usando o builder injetado automaticamente.
      //isso garante que a instancia do ChatClient esteja disponivel para injecao
    @Bean
    public ChatClient chatClient(ChatClient.Builder builder) {
        // O builder já está configurado com a chave de API do application.properties
        return builder.build();
    }
}