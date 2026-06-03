package com.example.novatecktibackend.dto;

public class OperacaoResponse {

    private StatusOperacao status;
    private String mensagem;

    public OperacaoResponse() {
    }

    public OperacaoResponse(StatusOperacao status, String mensagem) {
        this.status = status;
        this.mensagem = mensagem;
    }

    public static OperacaoResponse sucesso() {
        return new OperacaoResponse(StatusOperacao.SUCESSO, null);
    }

    public static OperacaoResponse erro(String mensagem) {
        return new OperacaoResponse(StatusOperacao.ERRO, mensagem);
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
}
