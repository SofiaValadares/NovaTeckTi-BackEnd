package com.example.novatecktibackend.service;

import com.example.novatecktibackend.dto.AtualizarSolicitacoesRequest;
import com.example.novatecktibackend.dto.OperacaoResponse;
import com.example.novatecktibackend.dto.SolicitacaoServicoTiDto;
import com.example.novatecktibackend.dto.SolicitacoesListaResponse;
import com.example.novatecktibackend.dto.StatusOperacao;
import com.example.novatecktibackend.entity.Cliente;
import com.example.novatecktibackend.entity.ServicoTi;
import com.example.novatecktibackend.entity.SolicitacaoServicoTi;
import com.example.novatecktibackend.mapper.EntityMapper;
import com.example.novatecktibackend.repository.SolicitacaoServicoTiRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SolicitacaoServicoTiService {

    private final SolicitacaoServicoTiRepository solicitacaoRepository;
    private final ClienteService clienteService;
    private final ServicoTiService servicoTiService;

    public SolicitacaoServicoTiService(
            SolicitacaoServicoTiRepository solicitacaoRepository,
            ClienteService clienteService,
            ServicoTiService servicoTiService
    ) {
        this.solicitacaoRepository = solicitacaoRepository;
        this.clienteService = clienteService;
        this.servicoTiService = servicoTiService;
    }

    @Transactional(readOnly = true)
    public SolicitacoesListaResponse listarPorLogin(String login) {
        Cliente cliente = clienteService.buscarPorLogin(login);
        if (cliente == null) {
            return new SolicitacoesListaResponse(
                    StatusOperacao.ERRO,
                    "Cliente não encontrado para o login informado.",
                    List.of()
            );
        }

        List<SolicitacaoServicoTiDto> solicitacoes = solicitacaoRepository
                .findByClienteLoginIgnoreCaseOrderByIdAsc(cliente.getLogin())
                .stream()
                .map(EntityMapper::toSolicitacaoDto)
                .toList();

        return new SolicitacoesListaResponse(StatusOperacao.SUCESSO, null, solicitacoes);
    }

    @Transactional
    public OperacaoResponse atualizarSolicitacoes(AtualizarSolicitacoesRequest request) {
        Cliente cliente = clienteService.buscarPorLogin(request.getLogin());
        if (cliente == null) {
            return OperacaoResponse.erro("Cliente não encontrado para o login informado.");
        }

        solicitacaoRepository.deleteByClienteId(cliente.getId());

        List<SolicitacaoServicoTi> novasSolicitacoes = new ArrayList<>();
        for (SolicitacaoServicoTiDto dto : request.getSolicitacoes()) {
            ServicoTi servico = servicoTiService.buscarPorId(dto.getServicoTiId());
            if (servico == null) {
                return OperacaoResponse.erro("Serviço de TI não encontrado: id " + dto.getServicoTiId());
            }

            SolicitacaoServicoTi solicitacao = new SolicitacaoServicoTi();
            solicitacao.setCliente(cliente);
            solicitacao.setServicoTi(servico);
            solicitacao.setDataSolicitacao(dto.getDataSolicitacao());
            solicitacao.setStatus(dto.getStatus());
            solicitacao.setPreco(dto.getPreco());
            solicitacao.setPrazoConclusao(dto.getPrazoConclusao());
            novasSolicitacoes.add(solicitacao);
        }

        solicitacaoRepository.saveAll(novasSolicitacoes);
        return OperacaoResponse.sucesso();
    }
}
