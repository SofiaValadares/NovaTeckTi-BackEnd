package com.example.novatecktibackend.dto;

public class ClientePerfilResponse {

    private StatusOperacao status;
    private String mensagem;
    private ClienteResponse cliente;

    public ClientePerfilResponse() {
    }

    public ClientePerfilResponse(StatusOperacao status, String mensagem, ClienteResponse cliente) {
        this.status = status;
        this.mensagem = mensagem;
        this.cliente = cliente;
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

    public ClienteResponse getCliente() {
        return cliente;
    }

    public void setCliente(ClienteResponse cliente) {
        this.cliente = cliente;
    }
}
