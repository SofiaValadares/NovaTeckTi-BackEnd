package com.example.novatecktibackend.dto;

public class AutenticacaoResponse {

    private StatusOperacao status;
    private boolean autenticado;
    private String mensagem;

    public AutenticacaoResponse() {
    }

    public AutenticacaoResponse(StatusOperacao status, boolean autenticado, String mensagem) {
        this.status = status;
        this.autenticado = autenticado;
        this.mensagem = mensagem;
    }

    public StatusOperacao getStatus() {
        return status;
    }

    public void setStatus(StatusOperacao status) {
        this.status = status;
    }

    public boolean isAutenticado() {
        return autenticado;
    }

    public void setAutenticado(boolean autenticado) {
        this.autenticado = autenticado;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
