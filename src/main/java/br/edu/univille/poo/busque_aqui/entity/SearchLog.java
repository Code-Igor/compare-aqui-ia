package br.edu.univille.poo.busque_aqui.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "search_log")
public class SearchLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String produto1;

    @Column(nullable = false)
    private String produto2;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String jsonResult;

    @Column(nullable = false)
    private LocalDateTime searchDate;

    public SearchLog() {
        this.searchDate = LocalDateTime.now();
    }

    public SearchLog(String produto1, String produto2, String jsonResult) {
        this();
        this.produto1 = produto1;
        this.produto2 = produto2;
        this.jsonResult = jsonResult;
    }

    public Long getId() {
        return id;
    }

    public String getProduto1() {
        return produto1;
    }
    public void setProduto1(String produto1) {
        this.produto1 = produto1;
    }

    public String getProduto2() {
        return produto2;
    }
    public void setProduto2(String produto2) {
        this.produto2 = produto2;
    }

    public String getJsonResult() {
        return jsonResult;
    }
    public void setJsonResult(String jsonResult) {
        this.jsonResult = jsonResult;
    }

    public LocalDateTime getSearchDate() {
        return searchDate;
    }
}