package com.example.novatecktibackend.service;

import com.example.novatecktibackend.dto.OperacaoResponse;
import com.example.novatecktibackend.dto.ServicoTiRequest;
import com.example.novatecktibackend.dto.ServicoTiResponse;
import com.example.novatecktibackend.dto.ServicosTiListaResponse;
import com.example.novatecktibackend.dto.StatusOperacao;
import com.example.novatecktibackend.entity.ServicoTi;
import com.example.novatecktibackend.mapper.EntityMapper;
import com.example.novatecktibackend.repository.ServicoTiRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ServicoTiService {

    private final ServicoTiRepository servicoTiRepository;

    public ServicoTiService(ServicoTiRepository servicoTiRepository) {
        this.servicoTiRepository = servicoTiRepository;
    }

    @Transactional
    public OperacaoResponse cadastrar(ServicoTiRequest request) {
        ServicoTi servico = new ServicoTi();
        servico.setNome(request.getNome().trim());
        servico.setPreco(request.getPreco());
        servico.setPrazoDias(request.getPrazoDias());
        servicoTiRepository.save(servico);
        return OperacaoResponse.sucesso();
    }

    @Transactional(readOnly = true)
    public ServicosTiListaResponse listarTodos() {
        List<ServicoTiResponse> servicos = servicoTiRepository.findAll().stream()
                .map(EntityMapper::toServicoTiResponse)
                .toList();
        return new ServicosTiListaResponse(StatusOperacao.SUCESSO, null, servicos);
    }

    public ServicoTi buscarPorId(Integer id) {
        return servicoTiRepository.findById(id).orElse(null);
    }
}
