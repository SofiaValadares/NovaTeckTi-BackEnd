package com.example.novatecktibackend.service;

import com.example.novatecktibackend.dto.ClientePerfilResponse;
import com.example.novatecktibackend.dto.ClienteRequest;
import com.example.novatecktibackend.dto.OperacaoResponse;
import com.example.novatecktibackend.dto.StatusOperacao;
import com.example.novatecktibackend.entity.Cliente;
import com.example.novatecktibackend.mapper.EntityMapper;
import com.example.novatecktibackend.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Transactional
    public OperacaoResponse cadastrar(ClienteRequest request) {
        String login = request.getLogin().trim();

        if (clienteRepository.existsByLoginIgnoreCase(login)) {
            return OperacaoResponse.erro("Já existe um cliente com este login.");
        }

        Cliente cliente = new Cliente();
        cliente.setNome(request.getNome().trim());
        cliente.setLogin(login);
        cliente.setSenha(request.getSenha());
        cliente.setCpf(request.getCpf().trim());
        cliente.setDataNascimento(request.getDataNascimento());
        cliente.setTelefone(request.getTelefone().trim());
        cliente.setEstadoCivil(request.getEstadoCivil().trim());
        cliente.setEscolaridade(request.getEscolaridade().trim());

        clienteRepository.save(cliente);
        return OperacaoResponse.sucesso();
    }

    public Cliente buscarPorLogin(String login) {
        return clienteRepository.findByLoginIgnoreCase(login.trim()).orElse(null);
    }

    public ClientePerfilResponse consultarPorLogin(String login) {
        Cliente cliente = buscarPorLogin(login);
        if (cliente == null) {
            return new ClientePerfilResponse(
                    StatusOperacao.ERRO,
                    "Cliente não encontrado para o login informado.",
                    null
            );
        }
        return new ClientePerfilResponse(
                StatusOperacao.SUCESSO,
                null,
                EntityMapper.toClienteResponse(cliente)
        );
    }
}
