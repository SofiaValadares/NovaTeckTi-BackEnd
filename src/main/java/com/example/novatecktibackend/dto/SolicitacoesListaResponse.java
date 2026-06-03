package com.example.novatecktibackend.dto;

import java.util.List;

public class SolicitacoesListaResponse {

    private StatusOperacao status;
    private String mensagem;
    private List<SolicitacaoServicoTiDto> solicitacoes;

    public SolicitacoesListaResponse() {
    }

    public SolicitacoesListaResponse(StatusOperacao status, String mensagem, List<SolicitacaoServicoTiDto> solicitacoes) {
        this.status = status;
        this.mensagem = mensagem;
        this.solicitacoes = solicitacoes;
    }

    public StatusOperacao getStatus() {
        return status;
    }

    public void setStatus(StatusOperacao status) {
        this.status = status;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public List<SolicitacaoServicoTiDto> getSolicitacoes() {
        return solicitacoes;
    }

    public void setSolicitacoes(List<SolicitacaoServicoTiDto> solicitacoes) {
        this.solicitacoes = solicitacoes;
    }
}
