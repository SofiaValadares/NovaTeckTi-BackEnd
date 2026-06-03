package com.example.novatecktibackend.service;

import com.example.novatecktibackend.dto.AutenticacaoResponse;
import com.example.novatecktibackend.dto.OperacaoResponse;
import com.example.novatecktibackend.dto.StatusOperacao;
import com.example.novatecktibackend.entity.Cliente;
import com.example.novatecktibackend.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AutenticacaoService {

    private final ClienteRepository clienteRepository;

    public AutenticacaoService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public boolean autenticar(String login, String senha) {
        return clienteRepository.findByLoginIgnoreCase(normalizarLogin(login))
                .map(cliente -> senha.equals(cliente.getSenha()))
                .orElse(false);
    }

    public AutenticacaoResponse autenticarComStatus(String login, String senha) {
        boolean autenticado = autenticar(login, senha);
        StatusOperacao status = autenticado ? StatusOperacao.SUCESSO : StatusOperacao.ERRO;
        String mensagem = autenticado ? null : "Login ou senha inválidos.";
        return new AutenticacaoResponse(status, autenticado, mensagem);
    }

    @Transactional
    public OperacaoResponse trocarSenha(String login, String senhaAtual, String novaSenha) {
        if (!autenticar(login, senhaAtual)) {
            return OperacaoResponse.erro("Autenticação inválida. Não foi possível trocar a senha.");
        }

        Cliente cliente = clienteRepository.findByLoginIgnoreCase(normalizarLogin(login))
                .orElseThrow();

        cliente.setSenha(novaSenha);
        clienteRepository.save(cliente);
        return OperacaoResponse.sucesso();
    }

    private String normalizarLogin(String login) {
        return login == null ? "" : login.trim();
    }
}
