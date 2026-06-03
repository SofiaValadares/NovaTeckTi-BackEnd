package com.example.novatecktibackend.dto;

import jakarta.validation.constraints.NotBlank;

public class TrocaSenhaRequest {

    @NotBlank
    private String login;

    @NotBlank
    private String senhaAtual;

    @NotBlank
    private String novaSenha;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenhaAtual() {
        return senhaAtual;
    }

    public void setSenhaAtual(String senhaAtual) {
        this.senhaAtual = senhaAtual;
    }

    public String getNovaSenha() {
        return novaSenha;
    }

    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }
}
