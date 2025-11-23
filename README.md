# Spring Rest Gemini AI Project: Busque Aqui

"Busque Aqui" is a small project for the OOP (POO) subject, the main objective was to integrate Spring Web with the Gemini AI rest API.

The system is a smart search of products on internet using Gemini, the user just type whatever product they want and then the system returns five different cards with the best prices of that product.


## Settings

| Item                     | Value                                 |
|--------------------------|---------------------------------------|
| Build Tool               | Maven                                 |
| Spring Boot Dependencies | Spring Web, Data JPA, AI and DevTools |
| DataBase                 | PostgreSQL                            |
| AI API                   | Gemini                                |
| Template Engine          | Thymeleaf                             |


## Integration

The integration is in the RealizarBuscaService class.

Used model: gemini-2.5-flash-lite.


## Prompt Engineering

The most important part, the prompt.

```
Aja como um especialista em comparação de preços e varejo digital. Sua tarefa é executar uma busca extremamente detalhada e em tempo real para o produto: [:PRODUTO].

Siga rigorosamente as seguintes etapas:

1.  Execução de Pesquisa Abrangente (Máxima Profundidade): Utilize o Google Search de forma ampla para encontrar resultados de e-commerce e de agregadores de preços para o produto especificado. Faça uma vasta pesquisa e certifique-se que passou pela maioria das grandes lojas.
2.  Foco em Resultados de Compra Recentes: Priorize links e dados que contenham a melhor oferta e garanta que os dados sejam os mais atuais possíveis.
3.  Filtragem e Estruturação: Analise os resultados da pesquisa para identificar e isolar os dados de nome da loja, preço e o link.
4.  Retorno Estruturado (Top 5): Selecione apenas os 5 resultados que apresentarem o valor mais baixo em relação a toda a pesquisa, pode haver mais de um resultado por loja. Seja inteligente, verifique qual foi o menor valor de toda a sua pesquisa e use esse valor com base para os outros produtos mais baratos do top 5.
Retorne esses resultados em JSON (com os dados filtrados: nome da loja, preço e o link). Retorne apenas esse JSON, nada a mais.

Inicie a pesquisa agora.
```


## SQL 

Database creation
```SQL
-- all the history will be persisted here
CREATE DATABASE busque_aqui_database;

-- the search log
CREATE TABLE SEARCH_LOG (
    id_search SERIAL PRIMARY KEY,
    user_query TEXT NOT NULL,
    json_result TEXT NOT NULL,
    search_time TIMESTAMP WITHOUT TIME ZONE
);

-- for each search log it will have five results
CREATE TABLE RESULT_LOG (
    id_result SERIAL PRIMARY KEY,
    id_search INT NOT NULL,
    store_name TEXT NOT NULL,
    price NUMERIC NOT NULL,
    link TEXT NOT null,
    
    CONSTRAINT fk_search FOREIGN KEY (id_search) REFERENCES SEARCH_LOG (id_search)
);
```
