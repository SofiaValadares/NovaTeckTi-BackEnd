package com.example.novatecktibackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SolicitacaoServicoTiDto {

    private Long id;

    @NotNull
    private LocalDate dataSolicitacao;

    @NotBlank
    private String status;

    @NotNull
    @Positive
    private BigDecimal preco;

    @NotNull
    private LocalDate prazoConclusao;

    @NotNull
    private Integer servicoTiId;

    private String nomeServico;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataSolicitacao() {
        return dataSolicitacao;
    }

    public void setDataSolicitacao(LocalDate dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public LocalDate getPrazoConclusao() {
        return prazoConclusao;
    }

    public void setPrazoConclusao(LocalDate prazoConclusao) {
        this.prazoConclusao = prazoConclusao;
    }

    public Integer getServicoTiId() {
        return servicoTiId;
    }

    public void setServicoTiId(Integer servicoTiId) {
        this.servicoTiId = servicoTiId;
    }

    public String getNomeServico() {
        return nomeServico;
    }

    public void setNomeServico(String nomeServico) {
        this.nomeServico = nomeServico;
    }
}
