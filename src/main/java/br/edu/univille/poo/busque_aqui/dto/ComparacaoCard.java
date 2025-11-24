package br.edu.univille.poo.busque_aqui.dto;

public class ComparacaoCard {

    // o recurso ou especificação que está sendo comparado
    private String recurso;

    // o valor ou descricao do recurso para o Produto 1
    private String valorProduto1;

    // o valor ou descricao do recurso para o Produto 2
    private String valorProduto2;

    // quem é o melhor neste recurso ("Produto 1", "Produto 2" ou "Empate")
    private String melhorEm;

    // construtor padrao
    public ComparacaoCard() {}

    public ComparacaoCard(String recurso, String valorProduto1, String valorProduto2, String melhorEm) {
        this.recurso = recurso;
        this.valorProduto1 = valorProduto1;
        this.valorProduto2 = valorProduto2;
        this.melhorEm = melhorEm;
    }

    // getters e setters

    public String getRecurso() {
        return recurso;
    }

    public void setRecurso(String recurso) {
        this.recurso = recurso;
    }

    public String getValorProduto1() {
        return valorProduto1;
    }

    public void setValorProduto1(String valorProduto1) {
        this.valorProduto1 = valorProduto1;
    }

    public String getValorProduto2() {
        return valorProduto2;
    }

    public void setValorProduto2(String valorProduto2) {
        this.valorProduto2 = valorProduto2;
    }

    public String getMelhorEm() {
        return melhorEm;
    }

    public void setMelhorEm(String melhorEm) {
        this.melhorEm = melhorEm;
    }
}