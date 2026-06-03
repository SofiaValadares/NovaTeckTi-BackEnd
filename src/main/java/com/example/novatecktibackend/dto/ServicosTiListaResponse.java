package com.example.novatecktibackend.dto;

import java.util.List;

public class ServicosTiListaResponse {

    private StatusOperacao status;
    private String mensagem;
    private List<ServicoTiResponse> servicos;

    public ServicosTiListaResponse() {
    }

    public ServicosTiListaResponse(StatusOperacao status, String mensagem, List<ServicoTiResponse> servicos) {
        this.status = status;
        this.mensagem = mensagem;
        this.servicos = servicos;
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

    public List<ServicoTiResponse> getServicos() {
        return servicos;
    }

    public void setServicos(List<ServicoTiResponse> servicos) {
        this.servicos = servicos;
    }
}
