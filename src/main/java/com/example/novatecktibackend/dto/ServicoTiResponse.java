package com.example.novatecktibackend.dto;

import java.math.BigDecimal;

public class ServicoTiResponse {

    private Integer id;
    private String nome;
    private BigDecimal preco;
    private Integer prazoDias;

    public ServicoTiResponse() {
    }

    public ServicoTiResponse(Integer id, String nome, BigDecimal preco, Integer prazoDias) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.prazoDias = prazoDias;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Integer getPrazoDias() {
        return prazoDias;
    }

    public void setPrazoDias(Integer prazoDias) {
        this.prazoDias = prazoDias;
    }
}
