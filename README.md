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

CREATE DATABASE compare_aqui_database;

-- persistindo os dados nessa tabela, cada comparação/pesquisa vai ficar guardada nessa tabela
CREATE TABLE search_log (
    id BIGSERIAL PRIMARY KEY,
    produto1 VARCHAR(255) NOT NULL,
    produto2 VARCHAR(255) NOT NULL,
    json_result TEXT, 
    search_date TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);
```
