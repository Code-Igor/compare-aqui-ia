# Spring Rest Gemini AI Project: Compare Aqui

"Compare Aqui" is a small project for the OOP (POO) subject, the main objective was to integrate Spring Web with the Gemini AI rest API.

Basically, the system is a product comparison tool: the user provides two products to the AI, which returns a comparison between them, showing which product is better.


## Settings

| Item                     | Value                                 |
|--------------------------|---------------------------------------|
| Build Tool               | Maven                                 |
| Spring Boot Dependencies | Spring Web, Data JPA, AI and DevTools |
| DataBase                 | PostgreSQL                            |
| AI API                   | Gemini                                |
| Template Engine          | Thymeleaf                             |


## Integration

The integration is in the ProdutoService class.

Used model: gemini-2.5-flash-lite.


## Prompt Engineering

The most important part, the prompt.

```
Você é um especialista em comparação de produtos. Sua tarefa é analisar e comparar os produtos %s e %s em 5 a 8 recursos chave.

            Você DEVE responder APENAS com um array JSON estrito (sem texto introdutório ou conclusivo) que se enquadre na lista de objetos JSON.

            O formato JSON esperado é:
            [{"recurso": "Nome do Recurso", "valorProduto1": "Valor do Produto 1", "valorProduto2": "Valor do Produto 2", "melhorEm": "Produto 1, Produto 2 ou Empate"}]

            A coluna "melhorEm" deve conter um dos três valores exatos: "Produto 1", "Produto 2" ou "Empate".
            
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
