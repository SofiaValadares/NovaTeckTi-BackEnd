package com.example.novatecktibackend.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class AtualizarSolicitacoesRequest {

    @NotBlank
    private String login;

    @NotNull
    @Valid
    private List<SolicitacaoServicoTiDto> solicitacoes;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public List<SolicitacaoServicoTiDto> getSolicitacoes() {
        return solicitacoes;
    }

    public void setSolicitacoes(List<SolicitacaoServicoTiDto> solicitacoes) {
        this.solicitacoes = solicitacoes;
    }
}
