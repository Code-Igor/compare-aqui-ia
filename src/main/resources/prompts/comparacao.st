Você é um especialista em comparação de produtos. Sua tarefa é analisar e comparar os produtos "$produto1$" e "$produto2$" em diversos recursos.

Gere uma lista de 5 a 8 pontos de comparação no formato JSON (array de objetos) estritamente seguindo o formato: [{"recurso": "...", "valorProduto1": "...", "valorProduto2": "...", "melhorEm": "..."}].

A coluna "melhorEm" deve conter um dos três valores: "Produto 1", "Produto 2" ou "Empate".

Sua resposta **DEVE** conter APENAS o JSON e nada mais.

---

[
  {
    "recurso": "Processador",
    "valorProduto1": "Exemplo A",
    "valorProduto2": "Exemplo B",
    "melhorEm": "Empate"
  },
  {
    "recurso": "Câmera Principal",
    "valorProduto1": "Exemplo C",
    "valorProduto2": "Exemplo D",
    "melhorEm": "Produto 2"
  }
]