package com.example.novatecktibackend.mapper;

import com.example.novatecktibackend.dto.ClienteResponse;
import com.example.novatecktibackend.dto.ServicoTiResponse;
import com.example.novatecktibackend.dto.SolicitacaoServicoTiDto;
import com.example.novatecktibackend.entity.Cliente;
import com.example.novatecktibackend.entity.ServicoTi;
import com.example.novatecktibackend.entity.SolicitacaoServicoTi;

public final class EntityMapper {

    private EntityMapper() {
    }

    public static ClienteResponse toClienteResponse(Cliente cliente) {
        return new ClienteResponse(
                cliente.getId(),
                cliente.getNome(),
                cliente.getLogin(),
                cliente.getCpf(),
                cliente.getDataNascimento(),
                cliente.getTelefone(),
                cliente.getEstadoCivil(),
                cliente.getEscolaridade()
        );
    }

    public static ServicoTiResponse toServicoTiResponse(ServicoTi servico) {
        return new ServicoTiResponse(
                servico.getId(),
                servico.getNome(),
                servico.getPreco(),
                servico.getPrazoDias()
        );
    }

    public static SolicitacaoServicoTiDto toSolicitacaoDto(SolicitacaoServicoTi solicitacao) {
        SolicitacaoServicoTiDto dto = new SolicitacaoServicoTiDto();
        dto.setId(solicitacao.getId());
        dto.setDataSolicitacao(solicitacao.getDataSolicitacao());
        dto.setStatus(solicitacao.getStatus());
        dto.setPreco(solicitacao.getPreco());
        dto.setPrazoConclusao(solicitacao.getPrazoConclusao());
        dto.setServicoTiId(solicitacao.getServicoTi().getId());
        dto.setNomeServico(solicitacao.getServicoTi().getNome());
        return dto;
    }
}
