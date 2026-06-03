package com.example.novatecktibackend.dto;

import java.time.LocalDate;

public class ClienteResponse {

    private Long id;
    private String nome;
    private String login;
    private String cpf;
    private LocalDate dataNascimento;
    private String telefone;
    private String estadoCivil;
    private String escolaridade;

    public ClienteResponse() {
    }

    public ClienteResponse(
            Long id,
            String nome,
            String login,
            String cpf,
            LocalDate dataNascimento,
            String telefone,
            String estadoCivil,
            String escolaridade
    ) {
        this.id = id;
        this.nome = nome;
        this.login = login;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
        this.estadoCivil = estadoCivil;
        this.escolaridade = escolaridade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getEscolaridade() {
        return escolaridade;
    }

    public void setEscolaridade(String escolaridade) {
        this.escolaridade = escolaridade;
    }
}
